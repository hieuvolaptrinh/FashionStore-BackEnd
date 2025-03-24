package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseId;

    private String purchaseDate;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseOrderDetail> purchaseOrderDetails;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier supplier;

    public PurchaseOrder() {
    }
}
