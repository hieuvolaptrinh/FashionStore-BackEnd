package com.HieuVo.BookStore_BackEnd.Model;

import java.util.List;

import jakarta.persistence.*;
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

    private double fee;

    @OneToMany(mappedBy = "paymentType")
    private List<Order> listOrder;
}
