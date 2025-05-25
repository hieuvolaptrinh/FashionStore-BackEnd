package com.HieuVo.FashionStore_BackEnd.Model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int shippingMethodId;

    @Column(columnDefinition = "NVARCHAR(256)")
    @NotBlank(message = "Tên phương thức vận chuyển không được để trống")
    @Size(min = 2, max = 256, message = "Tên phương thức vận chuyển phải có độ dài từ 2 đến 256 ký tự")
    private String shippingMethodName;


    @Column(columnDefinition = "NVARCHAR(256)")
    private String description;

    @Min(value = 0, message = "Phí phải lớn hơn hoặc bằng 0")
    private double fee;

    @OneToMany(mappedBy = "shippingMethod")
    private List<Order> listOrder;
}
