package com.HieuVo.FashionStore_BackEnd.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private int orderId;
    private long amount; // tổng tiền
    private String orderInfo;
    private String bankCode;
    private String language;
    private String ipAddress;
}
