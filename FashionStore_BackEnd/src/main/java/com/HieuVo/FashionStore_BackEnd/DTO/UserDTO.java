package com.HieuVo.FashionStore_BackEnd.DTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
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
