package com.HieuVo.FashionStore_BackEnd.Model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity

public class PaymentType {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int paymentTypeID;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String paymentTypeName;

    private String description;

    @Min(value = 0, message = "Phí phải lớn hơn hoặc bằng 0")
    private double fee;

    @OneToMany(mappedBy = "paymentType")
    private List<Order> listOrder;
}
