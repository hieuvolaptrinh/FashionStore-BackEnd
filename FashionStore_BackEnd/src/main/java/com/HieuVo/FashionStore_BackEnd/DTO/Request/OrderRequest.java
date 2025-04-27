package com.HieuVo.FashionStore_BackEnd.DTO.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Mã địa chỉ không được để trống")
    @Min(value = 1, message = "Mã địa chỉ không hợp lệ")
    private Integer addressId;

    @NotNull(message = "Mã phương thức thanh toán không được để trống")
    @Min(value = 1, message = "Mã phương thức thanh toán không hợp lệ")
    private Integer paymentTypeId;

    @NotNull(message = "Mã phương thức vận chuyển không được để trống")
    @Min(value = 1, message = "Mã phương thức vận chuyển không hợp lệ")
    private Integer shippingMethodId;

    @NotNull(message = "Danh sách sản phẩm không được để trống")
    @Size(min = 1, message = "Phải có ít nhất 1 sản phẩm trong đơn hàng")
    private List<Integer> selectedIds;
}