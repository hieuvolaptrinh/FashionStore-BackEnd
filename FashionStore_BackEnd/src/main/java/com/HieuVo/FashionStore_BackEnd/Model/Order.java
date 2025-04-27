package com.HieuVo.FashionStore_BackEnd.Model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
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

        @NotBlank(message = "Trạng thái không được để trống")
        @Size(min = 2, max = 500, message = "Trạng thái phải có độ dài từ 2 đến 500 ký tự")
        @Column(columnDefinition = "NVARCHAR(500)")
        private String status;


        @Min(value = 0, message = "Tổng giá phải lớn hơn hoặc bằng 0")
        private double totalPrice;

        private boolean isPay = false;


        @ManyToOne(cascade = {
                        jakarta.persistence.CascadeType.PERSIST,
                        jakarta.persistence.CascadeType.MERGE,
                        jakarta.persistence.CascadeType.DETACH,
                        jakarta.persistence.CascadeType.REFRESH
        })
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        // địa chỉ giao hàng

        @ManyToOne(cascade = {
                        jakarta.persistence.CascadeType.PERSIST,
                        jakarta.persistence.CascadeType.MERGE,
                        jakarta.persistence.CascadeType.DETACH,
                        jakarta.persistence.CascadeType.REFRESH
        })
        @JoinColumn(name = "shipping_address_id", nullable = false)
        private Address shippingAddress;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
        private List<OrderDetail> orderDetails;

        @ManyToOne(cascade = {
                        jakarta.persistence.CascadeType.PERSIST,
                        jakarta.persistence.CascadeType.MERGE,
                        jakarta.persistence.CascadeType.DETACH,
                        jakarta.persistence.CascadeType.REFRESH
        })
        @JoinColumn(name = "payment_type_id")
        private PaymentType paymentType;

        @ManyToOne(cascade = {
                        jakarta.persistence.CascadeType.PERSIST,
                        jakarta.persistence.CascadeType.MERGE,
                        jakarta.persistence.CascadeType.DETACH,
                        jakarta.persistence.CascadeType.REFRESH
        })
        @JoinColumn(name = "shipping_method_id")
        private ShippingMethod shippingMethod;

        @PrePersist
        public void beforePersist() {
                this.createAt = new Date(System.currentTimeMillis());
        }

        // sử dụng trigger để cập nhật số lượng sản phẩm trong kho hoặc viết ở ây luôn
        // cx đcgọi là lifecycle method
}
