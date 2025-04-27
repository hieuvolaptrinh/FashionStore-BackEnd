package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "PurchaseOrderDetail")
public class PurchaseOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseOrderDetailId;

    @NotNull(message = "Giá mua không được để trống")
    @Min(value = 1, message = "Giá mua phải lớn hơn 0")
    private double purchasePrice;

    @NotNull(message = "Số lượng mua không được để trống")
    @Min(value = 1, message = "Số lượng mua phải lớn hơn 0")
    private int quantityPurchased;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "purchaseId")
    private PurchaseOrder purchaseOrder;

    public PurchaseOrderDetail() {
    }
}