package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.Request.RestPasswordRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.Notification;
import com.HieuVo.FashionStore_BackEnd.DTO.Request.AuthRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.AuthResponse;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Service.JwtService;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
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
    private final UserService userService;
    private final AuthenticationManager authenticationManager; // cần phải vết bên security

    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
                if(user.isActive()==false){
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
    public ResponseEntity<?> resetPassword(@RequestBody RestPasswordRequest restPasswordRequest) {
      return this.userService.restPassword(restPasswordRequest);
    }
}
