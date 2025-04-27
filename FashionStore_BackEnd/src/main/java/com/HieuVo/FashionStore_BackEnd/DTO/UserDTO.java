package com.HieuVo.FashionStore_BackEnd.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String userName;
    private String phoneNumber;
    private byte[] avatarData; // Trường này vẫn giữ để tương thích với code cũ
    private String avatarBase64; // Thêm trường này để nhận dữ liệu từ frontend
    private List<String> roles;
    private Boolean active;

    public UserDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public UserDTO() {
    }

    public UserDTO(String password, String userName) {
        this.password = password;
        this.userName = userName;
    }


}
