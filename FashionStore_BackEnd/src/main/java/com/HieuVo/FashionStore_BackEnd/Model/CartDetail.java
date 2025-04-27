package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class CartDetail {
        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private int cartDetailId;

        @NotNull(message = "Số lượng không được để trống")
        @Min(value = 1, message = "Số lượng phải lớn hơn 0")
        private int quantity;

        @NotNull(message = "Giá không được để trống")
        @Min(value = 0, message = "Giá phải lớn hơn hoặc bằng 0")
        private double price;

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
        @JoinColumn(name = "cart_id")
        private Cart cart;

        @Override
        public String toString() {
                return "CartDetail{" +
                                "cartDetailId=" + cartDetailId +
                                ", quantity=" + quantity +
                                ", price=" + price +
                                // trnhs lặp vô hạn
                                ", cartId=" + (cart != null ? cart.getCartId() : null) +

                                ", productId=" + (product != null ? product.getProductId() : null) +
                                '}';
        }
}
