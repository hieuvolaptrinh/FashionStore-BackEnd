package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import lombok.Data;
import java.sql.Date;

@Data
public class CartDTOResponse {
    private int cartId;
    private Date createAt;
    private Date updateAt;
    private double totalPrices;

}