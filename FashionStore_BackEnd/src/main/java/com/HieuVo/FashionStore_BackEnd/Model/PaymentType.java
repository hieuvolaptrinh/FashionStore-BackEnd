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

public class PaymentType {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int paymentTypeID;

    @NotBlank(message = "Tên phương thức thanh toán không được để trống")
    @Column(columnDefinition = "NVARCHAR(500)")
    private String paymentTypeName;


    private String description;

    @NotNull(message = "Phí không được để trống")
    @Min(value = 0, message = "Phí phải lớn hơn hoặc bằng 0")
    private double fee;

    @OneToMany(mappedBy = "paymentType")
    private List<Order> listOrder;
}
