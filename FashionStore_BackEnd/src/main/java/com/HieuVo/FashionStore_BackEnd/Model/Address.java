package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @NotBlank(message = "Tên đường không được để trống")
    @Size(min = 2, max = 256, message = "Tên đường phải có độ dài từ 2 đến 256 ký tự")
    @Column(columnDefinition = "NVARCHAR(256)")
    private String streetName;

    @NotBlank(message = "Tên thành phố không được để trống")
    @Size(min = 2, max = 256, message = "Tên thành phố phải có độ dài từ 2 đến 256 ký tự")
    @Column(columnDefinition = "NVARCHAR(256)")
    private String cityName;

    @NotBlank(message = "Tên quận/huyện không được để trống")
    @Size(min = 2, max = 256, message = "Tên quận/huyện phải có độ dài từ 2 đến 256 ký tự")
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
