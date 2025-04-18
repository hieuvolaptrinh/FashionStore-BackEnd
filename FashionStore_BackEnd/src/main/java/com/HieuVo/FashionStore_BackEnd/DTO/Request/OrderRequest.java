package com.HieuVo.FashionStore_BackEnd.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer addressId;
    private Integer paymentTypeId;
    private Integer shippingMethodId;
    private List<Integer> selectedIds; // cartDetailIds
}