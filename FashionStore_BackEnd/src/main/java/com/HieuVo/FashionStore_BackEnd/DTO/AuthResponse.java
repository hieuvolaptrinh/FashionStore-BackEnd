package com.HieuVo.FashionStore_BackEnd.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class AuthResponse {
    private  String token;
    private String username; // Tên người dùng (optional)
    private List<String> roles; // Roles của người dùng (optional)

    public AuthResponse(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}

