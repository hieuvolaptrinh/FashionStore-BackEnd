package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingMethodResponse {
    private int shippingMethodId;
    private String shippingMethodName;
    private String description;
    private double fee;

}
