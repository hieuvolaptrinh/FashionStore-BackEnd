package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {
    private int cartDetailId;
    private int quantity;
    private double price;
    private ProductCartResponse product;
}