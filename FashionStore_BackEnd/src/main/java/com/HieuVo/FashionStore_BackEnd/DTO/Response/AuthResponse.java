package com.HieuVo.FashionStore_BackEnd.DTO.Response;
import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private  String token;
    private String username; // Tên người dùng (optional)
    private List<String> roles; // Roles của người dùng (optional)

    public AuthResponse(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
    public AuthResponse() {

    }

}

