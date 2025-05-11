package com.HieuVo.FashionStore_BackEnd.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private int addressId;

    @NotBlank(message = "Tên đường không được để trống")
    @Size(min = 2, max = 256, message = "Tên đường phải có độ dài từ 2 đến 256 ký tự")
    private String streetName;

    @NotBlank(message = "Tên thành phố không được để trống")
    @Size(min = 2, max = 256, message = "Tên thành phố phải có độ dài từ 2 đến 256 ký tự")
    private String cityName;

    @NotBlank(message = "Tên quận/huyện không được để trống")
    @Size(min = 2, max = 256, message = "Tên quận/huyện phải có độ dài từ 2 đến 256 ký tự")
    private String districtName;

    @NotBlank(message = "Tên phường/xã không được để trống")
    @Size(min = 2, max = 256, message = "Tên phường/xã phải có độ dài từ 2 đến 256 ký tự")
    private String wardName;
}
