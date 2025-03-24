package com.HieuVo.FashionStore_BackEnd.DTO;

import com.HieuVo.FashionStore_BackEnd.Model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductDTO {
    private int productId;
    private String productName;
    private String description;
    private double originalPrice;
    private String productionInfor;
    private double salePrice;
    private int quantity;
    private Date manufactureDate;
    private float avgStars;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.originalPrice = product.getOriginalPrice();
        this.productionInfor = product.getProductionInfor();
        this.salePrice = product.getSalePrice();
        this.quantity = product.getQuantity();
        this.manufactureDate = product.getManufactureDate();
        this.avgStars = product.getAvgStars();
    }
}
