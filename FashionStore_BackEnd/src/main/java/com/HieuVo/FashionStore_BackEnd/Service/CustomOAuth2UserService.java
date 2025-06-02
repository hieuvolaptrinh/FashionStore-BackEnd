package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.Model.Role;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.RoleRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * BƯỚC 1: Service xử lý xác thực người dùng qua OAuth2 (Google)
 * - Nhận thông tin từ Google
 * - Tạo mới hoặc cập nhật người dùng trong database
 * - Chuyển đổi thông tin người dùng thành OAuth2User
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("Bắt đầu xử lý thông tin người dùng từ Google...");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Trích xuất thông tin người dùng từ OAuth2User
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String pictureUrl = oAuth2User.getAttribute("picture");
        String givenName = oAuth2User.getAttribute("given_name"); // Tên
        String familyName = oAuth2User.getAttribute("family_name"); // Họ

        System.out.println("Thông tin từ Google: Email=" + email + ", Tên đầy đủ=" + name);
        System.out.println("Họ=" + familyName + ", Tên=" + givenName + ", Avatar=" + pictureUrl);

        // Kiểm tra và tạo/cập nhật người dùng
        User user = processUserDetails(email, name, pictureUrl, givenName, familyName);
        System.out.println("Đã xử lý người dùng: " + user.getUserName());

        // Tạo danh sách quyền từ vai trò của người dùng
        Set<SimpleGrantedAuthority> authorities = user.getListRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());

        // Tạo map thuộc tính bổ sung
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("userId", user.getUserId());
        attributes.put("username", user.getUserName());

        System.out.println("Hoàn tất xử lý thông tin người dùng, chuyển sang OAuth2SuccessHandler");
        return new DefaultOAuth2User(
                authorities,
                attributes,
                userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                        .getUserNameAttributeName());
    }

    /**
     * Xử lý thông tin người dùng: tạo mới nếu chưa tồn tại hoặc cập nhật nếu đã tồn
     * tại
     */
    private User processUserDetails(String email, String name, String pictureUrl, String givenName, String familyName) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            System.out.println("Tạo mới người dùng với email: " + email);
            // Tạo mới người dùng
            user = new User();
            user.setEmail(email);

            // Thiết lập firstName và lastName từ thông tin Google
            if (givenName != null && familyName != null) {
                user.setFirstName(givenName);
                user.setLastName(familyName);
            } else {
                // Tách tên thành firstName và lastName nếu không có givenName và familyName
                String[] nameParts = name.split(" ");
                if (nameParts.length > 1) {
                    user.setFirstName(nameParts[0]);
                    user.setLastName(name.substring(nameParts[0].length()).trim());
                } else {
                    user.setFirstName(name);
                    user.setLastName("");
                }
            }

            // Tạo username từ email và một chuỗi ngẫu nhiên
            String username = email.split("@")[0] + "_" + "Google";
            user.setUserName(username);

            // Thiết lập các giá trị khác
            user.setAvataUrl(pictureUrl);
            user.setActive(true);

            user.setPassword(passwordEncoder.encode("123456"));

            // Gán quyền USER
            Role userRole = roleRepository.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Role USER not found"));

            List<Role> roles = new ArrayList<>();
            roles.add(userRole);
            user.setListRoles(roles);
        } else {
            System.out.println("Cập nhật thông tin người dùng hiện có: " + user.getUserName());

            // Luôn cập nhật avatar URL mới nhất từ Google
            if (pictureUrl != null) {
                user.setAvataUrl(pictureUrl);
                System.out.println("Đã cập nhật avatar URL: " + pictureUrl);
            }

            // Cập nhật firstName và lastName nếu có
            if (givenName != null && familyName != null) {
                user.setFirstName(givenName);
                user.setLastName(familyName);
                System.out.println("Đã cập nhật tên: " + givenName + " " + familyName);
            }

            // Đảm bảo người dùng vẫn active
            if (!user.isActive()) {
                user.setActive(true);
                System.out.println("Đã kích hoạt lại tài khoản: " + user.getUserName());
            }
        }

        return userRepository.save(user);
    }
}