package com.HieuVo.FashionStore_BackEnd.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Integer userId;

    @NotBlank(message = "Họ không được để trống")
    @Size(min = 2, max = 50, message = "Họ phải có độ dài từ 2 đến 50 ký tự")
    private String firstName;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 50, message = "Tên phải có độ dài từ 2 đến 50 ký tự")
    private String lastName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Mật khẩu phải chứa ít nhất 1 chữ số, 1 chữ hoa, 1 chữ thường và 1 ký tự đặc biệt")
    private String password;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 4, max = 20, message = "Tên đăng nhập phải có độ dài từ 4 đến 20 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Tên đăng nhập chỉ được chứa chữ cái và số")
    private String userName;

    // @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    private byte[] avatarData;
    private String avatarBase64;
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
