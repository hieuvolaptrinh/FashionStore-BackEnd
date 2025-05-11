package com.HieuVo.FashionStore_BackEnd.DTO.Request;

import com.HieuVo.FashionStore_BackEnd.Model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ProductRequest {
    private int productId;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 256, message = "Tên sản phẩm phải có độ dài từ 2 đến 256 ký tự")
    private String productName;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotNull(message = "Giá gốc không được để trống")
    @Min(value = 1, message = "Giá gốc phải lớn hơn 0")
    private double originalPrice;

    private String productionInfor;

    @NotNull(message = "Giá bán không được để trống")
    @Min(value = 1, message = "Giá bán phải lớn hơn 0")
    private double salePrice;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private int quantity;

    private Date manufactureDate;

    private List<Integer> listTypes;

    private List<Long> deletedImageIds; // Danh sách ID hình ảnh cần xóa

}
