package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class OrderDetail {
        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private int orderDetailId;

        @NotNull(message = "Số lượng không được để trống")
        @Min(value = 1, message = "Số lượng phải lớn hơn 0")
        private int quantity;

        @NotNull(message = "Giá không được để trống")
        @Min(value = 1, message = "Giá phải lớn hơn 0")
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
        @JoinColumn(name = "order_id")
        private Order order;
}
