package com.HieuVo.FashionStore_BackEnd.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeResponse {
    private int paymentTypeId;
    private String paymentTypeName;
    private String description;
    private double fee;
}
