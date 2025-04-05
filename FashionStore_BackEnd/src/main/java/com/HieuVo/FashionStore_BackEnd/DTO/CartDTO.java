package com.HieuVo.FashionStore_BackEnd.DTO;

import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
public class CartDTO {
    private int cartId;
    private Date createAt;
    private Date updateAt;
    private double totalPrices;
    private List<CartDetailDTO> cartDetails;
}