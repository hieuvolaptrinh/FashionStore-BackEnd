package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.Request.UserRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.UserResponse;
import com.HieuVo.FashionStore_BackEnd.Model.Role;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.RoleRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
                rolesToAuthorites(user.getListRoles()));

    }
    // Lấy danh sách role của user, tránh lỗi nếu roles == null
    // vì chỗ này trả về GrantedAuthority nên bên security phải sử dụng
    // hasAuthority("ADMIN") thay vì hasRole("ADMIN")
    private Collection<? extends GrantedAuthority> rolesToAuthorites(Collection<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of(new SimpleGrantedAuthority("USER")); // Gán quyền mặc định nếu user chưa có quyền
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    public User fetchUserByUsername(String username) {
        Optional<User> user = this.userRepository.findByUserName(username);
        if (user.isPresent()) {
            return user.get();
        }
        return null;

    }


    private UserResponse convertToDTO(User user) {
        UserResponse userDTO = new UserResponse();
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
    }

    public UserResponse getUser(String username) {
        Optional<User> user = this.userRepository.findByUserName(username);
        System.out.println("--------------------------------get oke---------------: " + username);
        return convertToDTO(user.orElse(null));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //
    public String updateUser(Integer userId, UserRequest userDTO) {
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "Không tìm thấy người dùng với ID: " + userId;
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setActive(userDTO.getActive());
        if(userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                    }
        // Không update password và username
        List<Role> roles = this.roleRepository.findAllByRoleNameIn(userDTO.getRoles());
        user.setListRoles(roles);
        if (userDTO.getAvatarBase64() != null && !userDTO.getAvatarBase64().isEmpty()) {
            // Chuyển đổi chuỗi base64 thành byte[] trước khi lưu
            user.setAvatarData(Base64.getDecoder().decode(userDTO.getAvatarBase64()));
        }
        System.out.println("đã cập nhật user: " + user.getUserName() + " với các quyền: " + user.getListRoles());
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
