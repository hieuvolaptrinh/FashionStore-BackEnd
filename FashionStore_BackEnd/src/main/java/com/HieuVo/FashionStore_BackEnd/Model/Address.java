package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String streetName;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String cityName;

    @Column(columnDefinition = "NVARCHAR(256)")

    private String districtName;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String wardName;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shippingAddress")
    private List<Order> listOrders;

    @OneToMany(mappedBy = "address")
    private List<Supplier> listSuppliers;
}
