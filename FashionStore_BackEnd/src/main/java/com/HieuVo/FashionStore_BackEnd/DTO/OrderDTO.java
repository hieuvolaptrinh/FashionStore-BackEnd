package com.HieuVo.FashionStore_BackEnd.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer addressId;
    private Integer paymentTypeId;
    private Integer shippingMethodId;
    private List<Integer> selectedIds; // cartDetailIds
}