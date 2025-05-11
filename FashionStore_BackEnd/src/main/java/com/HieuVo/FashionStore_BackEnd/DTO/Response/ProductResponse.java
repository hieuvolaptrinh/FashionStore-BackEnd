package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import com.HieuVo.FashionStore_BackEnd.Model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private int productId;

    private String productName;


    private String description;

    private double originalPrice;


    private String productionInfor;


    private double salePrice;


    private int quantity;


    private Date manufactureDate;

    private float avgStars;

    private List<ImageResponse> listImages;
    private List<TypeResponse> listTypes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageResponse {
        private int imageId;
        private String link;

    }

    public ProductResponse(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.originalPrice = product.getOriginalPrice();
        this.salePrice = product.getSalePrice();
        this.quantity = product.getQuantity();
        this.manufactureDate = product.getManufactureDate();
        this.avgStars = product.getAvgStars();
        this.productionInfor= product.getProductionInfor();
        if (product.getListTypes() != null) {
            this.listTypes = product.getListTypes().stream().map(type -> new TypeResponse(type.getTypeId(), type.getTypeName())).toList();
        }
        if (product.getListImages() != null) {
            this.listImages = product.getListImages().stream().map(image -> new ImageResponse(image.getImageId(), image.getLink())).toList();
        }
    }

}
