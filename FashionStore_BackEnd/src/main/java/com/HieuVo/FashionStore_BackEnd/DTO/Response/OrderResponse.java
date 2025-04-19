package com.HieuVo.FashionStore_BackEnd.DTO.Response;

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
public class OrderResponse {
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
        private int orderDetailId;
        private int quantity;
        private double price;
        private String mainImage;
        private String productName;
        private String description;

    }
}
