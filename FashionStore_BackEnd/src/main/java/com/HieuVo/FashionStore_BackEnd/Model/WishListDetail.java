package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class WishListDetail {
        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private int wishListDetailId;


        @ManyToOne(cascade = {
                        jakarta.persistence.CascadeType.PERSIST,
                        jakarta.persistence.CascadeType.MERGE,
                        jakarta.persistence.CascadeType.DETACH,
                        jakarta.persistence.CascadeType.REFRESH
        })
        @JoinColumn(name = "product_id")
        private Product product;


        @ManyToOne(cascade = {
                        jakarta.persistence.CascadeType.PERSIST,
                        jakarta.persistence.CascadeType.MERGE,
                        jakarta.persistence.CascadeType.DETACH,
                        jakarta.persistence.CascadeType.REFRESH
        })
        @JoinColumn(name = "wish_list_id")
        private WishList wishList;
}
