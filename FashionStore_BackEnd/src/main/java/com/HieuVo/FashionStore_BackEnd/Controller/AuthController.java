package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.AuthRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.AuthResponse;
import com.HieuVo.FashionStore_BackEnd.Service.JwtService;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationServiceException;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")  // cho phép gọi từ các domain khác để test đã
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
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUserName(),
                            authRequest.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                // Lấy roles của người dùng từ Authentication
                String roles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));


                final String jwt = jwtService.generateToken(authRequest.getUserName());
                AuthResponse authResponse = new AuthResponse(jwt, authRequest.getUserName(), roles);
                return ResponseEntity.ok(authResponse);
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage()+" tên đăng nhập hoặc mật khẩu sai");
        }
        return ResponseEntity.badRequest().body("Đăng nhâp thất bại");
    }
}
