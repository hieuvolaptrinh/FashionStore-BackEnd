package com.HieuVo.FashionStore_BackEnd.DTO;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTO {
    private int cartDetailId;
    private int quantity;
    private double price;
    private ProductCartDTO product;
}