package com.HieuVo.FashionStore_BackEnd.DTO;

import lombok.Data;

@Data
public class ProductCartDTO {
    private int productId;
    private String productName;
    private String description;
    private double originalPrice;
    private double salePrice;
    private String productionInfor;
    private String mainImage;
}