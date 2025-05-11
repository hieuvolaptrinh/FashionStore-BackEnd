package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.UserDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.Notification;
import com.HieuVo.FashionStore_BackEnd.Model.Role;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.AdderssRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.RoleRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;

import com.HieuVo.FashionStore_BackEnd.Util.Mail.SendEmailController;
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

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;


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

    public User fetchUserByUsername(String username) {
        Optional<User> user = this.userRepository.findByUserName(username);
        if (user.isPresent()) {
            return user.get();
        }
        return null;

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
