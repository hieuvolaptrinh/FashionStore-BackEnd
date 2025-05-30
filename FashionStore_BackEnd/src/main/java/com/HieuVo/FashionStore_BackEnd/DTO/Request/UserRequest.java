package com.HieuVo.FashionStore_BackEnd.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    private Integer userId;


    private String firstName;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 50, message = "Tên phải có độ dài từ 2 đến 50 ký tự đang test")
    private String lastName;

    @NotBlank(message = "Email không được để trống")
    private String email;

    private String password;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 4, max = 20, message = "Tên đăng nhập phải có độ dài từ 4 đến 20 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Tên đăng nhập chỉ được chứa chữ cái và số")
    private String userName;


    private String phoneNumber;

    private byte[] avatarData;
    private String avatarBase64;
    private List<String> roles;
    private Boolean active;

}
