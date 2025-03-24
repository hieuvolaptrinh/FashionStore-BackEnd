package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "Supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;
    @Column(nullable = false)
    private String supplierName;

    @Column(name = "phoneNumber", unique = true, length = 10)
    private String phoneNumber;


    @OneToMany(mappedBy = "supplier")
    private List<PurchaseOrder> purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "address_address_id")
    private Address address;


    public Supplier() {
    }

}
