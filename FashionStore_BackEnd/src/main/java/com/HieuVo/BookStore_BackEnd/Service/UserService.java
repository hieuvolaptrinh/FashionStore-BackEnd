package com.HieuVo.BookStore_BackEnd.Service;

import com.HieuVo.BookStore_BackEnd.DTO.UserDTO;
import com.HieuVo.BookStore_BackEnd.Model.Notification;
import com.HieuVo.BookStore_BackEnd.Model.Role;
import com.HieuVo.BookStore_BackEnd.Model.User;
import com.HieuVo.BookStore_BackEnd.Repository.RoleRepository;
import com.HieuVo.BookStore_BackEnd.Repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<?> registerUser(UserDTO userDTO) {
        if (this.userRepository.existsByUserName(userDTO.getUserName())) {
            return ResponseEntity.badRequest().body(new Notification("Tài khoản đã tồn tại"));
        }
        if (this.userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new Notification("Email đã tồn tại"));
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        user.setEmail(userDTO.getEmail());
        User newUser = this.userRepository.save(user);
        return ResponseEntity.ok("Đăng ký thành công");
    }


    //config security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User không tồn tại")); // vì bên kia mình sử dụng Optinal nên ở đây mình sử dụng orElseThrow

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
            return List.of(); // Trả về danh sách trống thay vì null
        }
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority( role.getRoleName()))
                .collect(Collectors.toList());
    }

}