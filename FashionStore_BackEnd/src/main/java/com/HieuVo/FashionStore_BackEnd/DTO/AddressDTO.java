package com.HieuVo.FashionStore_BackEnd.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private int addressId;
    private String streetName;

    private String cityName;

    private String districtName;

    private String wardName;

}
