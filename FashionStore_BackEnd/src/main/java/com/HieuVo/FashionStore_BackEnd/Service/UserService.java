package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.UserDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Notification;
import com.HieuVo.FashionStore_BackEnd.Model.Address;
import com.HieuVo.FashionStore_BackEnd.Model.Role;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.AdderssRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.RoleRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;

import com.HieuVo.FashionStore_BackEnd.Util.SendEmailController;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailController emailService;
    private final RoleRepository roleRepository;
    private final AdderssRepository addressRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       SendEmailController emailService, RoleRepository roleRepository,
                       AdderssRepository addressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;

    }

    public User fetchUserByUsername(String username) {
        Optional<User> user = this.userRepository.findByUserName(username);
        if (user.isPresent()) {
            return user.get();
        }
        return null;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User không tồn tại"));
        System.out.println("User found: " + user.getUserName());
        System.out.println("User roles: " + user.getListRoles());
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                rolesToAuthorites(user.getListRoles())
        );

    }

    // Lấy danh sách role của user, tránh lỗi nếu roles == null
//    vì chỗ này trả về GrantedAuthority nên bên security phải sử dụng hasAuthority("ADMIN") thay vì hasRole("ADMIN")
    private Collection<? extends GrantedAuthority> rolesToAuthorites(Collection<Role> roles) {

        if (roles == null || roles.isEmpty()) {
//            return List.of(new SimpleGrantedAuthority("USER")); // Gán quyền mặc định nếu user chưa có quyền
            return List.of(); // Trả về danh sách trống thay vì null]
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> registerUser(UserDTO userDTO) {
        if (this.userRepository.existsByUserName(userDTO.getUserName())) {
            return ResponseEntity.badRequest().body("Tài khoản đã tồn tại");
        }
        if (this.userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        user.setEmail(userDTO.getEmail());
        // Xử lý dữ liệu avatar
        if (userDTO.getAvatarBase64() != null && !userDTO.getAvatarBase64().isEmpty()) {
            // Chuyển đổi chuỗi base64 thành byte[] trước khi lưu
            user.setAvatarData(Base64.getDecoder().decode(userDTO.getAvatarBase64()));
        }

        user.setActivationCode(randomOTP());
        user.setActive(false);
        if (userDTO.getRoles() != null) {
            List<Role> roles = this.roleRepository.findAllByRoleNameIn(userDTO.getRoles());
        } else {
            user.setListRoles(Collections.singletonList(this.roleRepository.findByRoleName("USER")));

        }
//        Role role = this.roleRepository.findByRoleName("USER");
//        if (role == null) {
//            throw new RuntimeException("Role 'USER' không tồn tại trong database!");
//        }
//        user.setListRoles(new ArrayList<>(List.of(role)));

        User newUser = this.userRepository.save(user);
        //        send email
        sendActivationEmail(user.getEmail(), user.getActivationCode());
        return ResponseEntity.ok("Đăng ký thành công");
    }

    private String randomOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendActivationEmail(String email, String activationCode) {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("URL");
        String mail = dotenv.get("MAIL_USERNAME");
        String subject = "Kích hoạt tài khoản";

        // Tạo email HTML với thiết kế đẹp
        String htmlMessage =
                "<!DOCTYPE html>" +
                        "<html lang='vi'>" +
                        "<head>" +
                        "<meta charset='UTF-8'>" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "<title>Kích hoạt tài khoản</title>" +
                        "<style>" +
                        "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f9f9f9; }" +
                        ".container { max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }" +
                        ".header { background-color: #4285f4; color: white; padding: 20px; text-align: center; }" +
                        ".content { padding: 30px; }" +
                        ".code { background-color: #f5f5f5; padding: 15px; border-radius: 4px; text-align: center; margin: 20px 0; font-family: monospace; font-size: 24px; letter-spacing: 4px; }" +
                        ".button { display: inline-block; background-color: #4285f4; color: white; text-decoration: none; padding: 12px 30px; border-radius: 4px; margin-top: 20px; font-weight: bold; }" +
                        ".button:hover { background-color: #3367d6; }" +
                        ".footer { text-align: center; padding: 20px; color: #777; font-size: 12px; border-top: 1px solid #eee; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='container'>" +
                        "<div class='header'>" +
                        "<h1>Xác nhận tài khoản</h1>" +
                        "</div>" +
                        "<div class='content'>" +
                        "<p>Xin chào,</p>" +
                        "<p>Cảm ơn bạn đã đăng ký tài khoản tại Fashion Store. Để hoàn tất quá trình đăng ký, vui lòng xác nhận địa chỉ email của bạn.</p>" +
                        "<p>Mã kích hoạt của bạn là:</p>" +
                        "<div class='code'>" + activationCode + "</div>" +
                        "<p>Hoặc bạn có thể nhấn vào nút bên dưới để kích hoạt tài khoản ngay lập tức:</p>" +
                        "<div style='text-align: center;'>" +
                        "<a href='" + url + "/activateAccount/" + email + "/" + activationCode + "' class='button'>Kích hoạt tài khoản</a>" +
                        "</div>" +
                        "<p style='margin-top: 30px;'>Nếu bạn không yêu cầu tạo tài khoản này, bạn có thể bỏ qua email này.</p>" +
                        "</div>" +
                        "<div class='footer'>" +
                        "<p>© 2025 Fashion Store. Tất cả các quyền được bảo lưu.</p>" +
                        "</div>" +
                        "</div>" +
                        "</body>" +
                        "</html>";

        try {
            this.emailService.sendHtmlMessage(mail, email, subject, htmlMessage);
            System.out.println("Gửi mail kích hoạt thành công");
        } catch (MessagingException e) {
            System.err.println("Lỗi gửi email kích hoạt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ResponseEntity<Notification> confirmEmail(String email, String activationCode) {
        User user = this.userRepository.findByEmail(email);
        System.out.println("Request activation code: '" + activationCode + "'");
        System.out.println("DB activation code: '" + (user != null ? user.getActivationCode() : "null") + "'");

        if (user == null) {
            return ResponseEntity.ok(new Notification("Email không tồn tại"));
        }
        if (user.isActive()) {
            return ResponseEntity.ok(new Notification("Tài khoản đã được kích hoạt"));
        }
        if (activationCode.trim().equals(user.getActivationCode().trim())) {
            user.setActive(true);
            this.userRepository.save(user);
            return ResponseEntity.ok(new Notification("Kích hoạt tài khoản thành công"));
        } else {
            return ResponseEntity.ok(new Notification("Mã kích hoạt sai!"));
        }
    }


    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUserName());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setActive(user.isActive());
            userDTO.setRoles(user.getListRoles().stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toList()));

            userDTO.setPhoneNumber(user.getPhoneNumber());
            if (user.getAvatarData() != null) {
                userDTO.setAvatarBase64(Base64.getEncoder().encodeToString(user.getAvatarData()));
            }
            return userDTO;

        }).collect(Collectors.toList());
        return userDTOs;
    }

    //
    public String updateUser(Integer userId, UserDTO userDTO) {
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "Không tìm thấy người dùng với ID: " + userId;
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setActive(userDTO.getActive());

//        Không update password và username
        List<Role> roles = this.roleRepository.findAllByRoleNameIn(userDTO.getRoles());
        user.setListRoles(roles);
        if (userDTO.getAvatarBase64() != null && !userDTO.getAvatarBase64().isEmpty()) {
            // Chuyển đổi chuỗi base64 thành byte[] trước khi lưu
            user.setAvatarData(Base64.getDecoder().decode(userDTO.getAvatarBase64()));
        }
        this.userRepository.save(user);
        return "Cập nhật thành công";
    }

    public String lockAccount(Integer userId) {

        User user = this.userRepository.findById(userId).orElse(null);
        user.setActive(false);
        this.userRepository.save(user);
        return "Khóa người dùng thành công";
    }
}
