package com.HieuVo.BookStore_BackEnd.Model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int orderId;
    private Date createAt;

    private Date deliveryDate;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String status;

    private double totalPrice;

    @ManyToOne( cascade ={
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;


    //        địa chỉ giao hàng
    @ManyToOne( cascade ={
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "shipping_ward_id",nullable = false)
    private Ward shippingWard;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    @ManyToOne( cascade ={
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @ManyToOne( cascade ={
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "shipping_method_id")
    private ShippingMethod shippingMethod;


}
