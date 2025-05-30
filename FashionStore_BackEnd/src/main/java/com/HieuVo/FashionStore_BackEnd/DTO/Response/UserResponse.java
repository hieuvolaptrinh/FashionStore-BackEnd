package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Integer userId;

    private String firstName;

    private String lastName;


    private String email;


    private String password;


    private String userName;


    private String phoneNumber;

    private byte[] avatarData;
    private String avatarBase64;
    private List<String> roles;
    private Boolean active;

    public UserResponse(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserResponse() {
    }

    public UserResponse(String password, String userName) {
        this.password = password;
        this.userName = userName;
    }

}
