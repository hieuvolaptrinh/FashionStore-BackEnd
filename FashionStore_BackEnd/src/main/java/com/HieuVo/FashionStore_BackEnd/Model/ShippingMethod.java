package com.HieuVo.FashionStore_BackEnd.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int shippingMethodID;

    private String shippingMethodName;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String description;
    private double fee;
    @OneToMany(mappedBy = "shippingMethod")
    private List<Order> listOrder;

}
