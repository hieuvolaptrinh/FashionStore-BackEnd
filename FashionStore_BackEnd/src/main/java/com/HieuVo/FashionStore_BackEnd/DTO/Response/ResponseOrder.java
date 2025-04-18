package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrder {
    private int orderId;
    private String status;
    private double totalPrice;

    private Date createAt;

    private List<OrderDetailDTO> orderDetails;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailDTO {
        private Integer productId;
        private Integer quantity;
        private Double price;
    }
}
