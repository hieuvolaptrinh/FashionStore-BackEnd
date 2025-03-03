package com.HieuVo.BookStore_BackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wardId;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String wardName;

    @ManyToOne
    @JoinColumn(name = "district_id") // Đảm bảo không null
    private District district;

    @OneToMany(mappedBy = "address")
    private List<Users> listUsers;

    @OneToMany(mappedBy = "shippingWard")
    private List<Order> listOrders;

}
