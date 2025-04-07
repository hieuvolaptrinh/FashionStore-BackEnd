package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @NotNull(message = "Create date cannot be null")
    private Date createAt;

    @NotNull(message = "Update date cannot be null")
    private Date updateAt;

    @PositiveOrZero(message = "Total price must be greater than or equal to 0")
    private double totalPrices;

    // @NotNull(message = "User cannot be null")
    // @OneToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartDetail> listCartDetails;

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", totalPrices=" + totalPrices +
                // Thay vì in toàn bộ listCartDetails, chỉ in số lượng
                ", cartDetailsCount=" + (listCartDetails != null ? listCartDetails.size() : 0) +
                '}';
    }


}