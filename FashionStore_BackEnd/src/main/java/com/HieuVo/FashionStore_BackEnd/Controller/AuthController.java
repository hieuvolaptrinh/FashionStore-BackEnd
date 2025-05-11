package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.Request.RestPasswordRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.Notification;
import com.HieuVo.FashionStore_BackEnd.DTO.Request.AuthRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.AuthResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.UserDTO;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Service.AuthService;
import com.HieuVo.FashionStore_BackEnd.Service.JwtService;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;// cần phải vết bên security
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/activateAccount")
    public ResponseEntity<Notification> confirmNewUser(@RequestParam String email, @RequestParam String activationCode) throws Exception {
        return this.authService.confirmEmail(email, activationCode);
    }

    @PostMapping("/register")
    @ApiMessage("Đã đăng ký thành công, vui lòng kiểm tra email để kích hoạt tài khoản.")
    public ResponseEntity<Notification> registrerNewUser(@Valid @RequestBody UserDTO userDTO) {
        String response = this.authService.registerUser(userDTO);
        return ResponseEntity.ok(new Notification(response));
    }

    @PostMapping("/login")
    @ApiMessage("Login successfully")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUserName(),
                            authRequest.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                User user = this.userService.fetchUserByUsername(authRequest.getUserName());
                if (user.isActive() == false) {
                    return ResponseEntity.badRequest().body(new Notification("Tài khoản chưa được kích hoạt"));
                }
                // Lấy roles của người dùng từ Authentication
                List<String> roles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()); // Trả về List<String> thay vì String
                final String jwt = jwtService.generateToken(authRequest.getUserName(), roles);
                AuthResponse authResponse = new AuthResponse(jwt, authRequest.getUserName(), roles);
                System.out.println("JWT: " + jwt);
                System.out.println("Username: " + authRequest.getUserName());
                System.out.println("Roles: " + roles);
                return ResponseEntity.ok(authResponse);
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new Notification(" tên đăng nhập hoặc mật khẩu sai"));
        }
        return ResponseEntity.badRequest().body(new Notification("Đăng nhập không thành công"));
    }

    @PostMapping("/reset-password")
    @ApiMessage("Đã gửi email để lấy lại mật khẩu")
    public ResponseEntity<Notification> resetPassword(@RequestBody RestPasswordRequest restPasswordRequest) throws Exception {

        return ResponseEntity.ok(new Notification(this.authService.restPassword(restPasswordRequest)));
    }

    @PostMapping("/new-password")
    @ApiMessage("Cập nhật mật khẩu thành công")
    public ResponseEntity<Notification> newPassword(@RequestParam String email, @RequestParam String password, @RequestParam String activationCode) throws Exception {
        return ResponseEntity.ok(new Notification(this.authService.newPassword(email, password,activationCode)));

    }

}
