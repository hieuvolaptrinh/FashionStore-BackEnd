package com.HieuVo.BookStore_BackEnd.Model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartDetail {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int cartDetailId;

    private int quantity;

    private double price;

    @ManyToOne( cascade ={
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne( cascade ={
            jakarta.persistence.CascadeType.PERSIST,
            jakarta.persistence.CascadeType.MERGE,
            jakarta.persistence.CascadeType.DETACH,
            jakarta.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "cart_id")
    private Cart cart;


}
