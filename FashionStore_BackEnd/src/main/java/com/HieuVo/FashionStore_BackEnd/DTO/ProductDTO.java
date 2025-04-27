package com.HieuVo.FashionStore_BackEnd.DTO;

import com.HieuVo.FashionStore_BackEnd.Model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private int productId;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 256, message = "Tên sản phẩm phải có độ dài từ 2 đến 256 ký tự")
    private String productName;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(min = 10, max = 1000, message = "Mô tả phải có độ dài từ 10 đến 1000 ký tự")
    private String description;

    @NotNull(message = "Giá gốc không được để trống")
    @Min(value = 1, message = "Giá gốc phải lớn hơn 0")
    private double originalPrice;

    @NotBlank(message = "Thông tin sản xuất không được để trống")
    private String productionInfor;

    @NotNull(message = "Giá bán không được để trống")
    @Min(value = 1, message = "Giá bán phải lớn hơn 0")
    private double salePrice;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private int quantity;


    private Date manufactureDate;

    private float avgStars;

    private List<String> listImages;
    private List<Integer> listTypes;

    public ProductDTO() {
    }

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
