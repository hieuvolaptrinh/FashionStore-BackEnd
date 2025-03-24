package com.HieuVo.FashionStore_BackEnd.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "PurchaseOrderDetail")
public class PurchaseOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseOrderDetailId;

    private double purchasePrice;
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