package com.HieuVo.FashionStore_BackEnd.DTO.Response;


import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private String token;
    private String username;
    private List<String> roles;

    public AuthResponse(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public AuthResponse() {

    }

}
