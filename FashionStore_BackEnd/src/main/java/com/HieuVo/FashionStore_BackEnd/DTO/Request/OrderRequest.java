package com.HieuVo.FashionStore_BackEnd.DTO.Request;

import java.util.List;

public class OrderRequest {
    private int shippingMethodID;

    private int paymentTypeID;
    private List<Integer> listProductID;
}
