package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.Response.PaymentTypeResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ShippingMethodResponse;
import com.HieuVo.FashionStore_BackEnd.Model.PaymentType;
import com.HieuVo.FashionStore_BackEnd.Repository.OrderRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.PaymentTypeRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.ShippingMethodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final ShippingMethodRepository shippingMethodRepository;

    public OrderService(OrderRepository orderRepository, PaymentTypeRepository paymentTypeRepository, ShippingMethodRepository shippingMethodRepository) {
        this.orderRepository = orderRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.shippingMethodRepository = shippingMethodRepository;
    }

    public List<PaymentTypeResponse> findAllPaymentType() {
        List<PaymentType> paymentTypes = paymentTypeRepository.findAll();

        return paymentTypes.stream().map(paymentType -> {
            PaymentTypeResponse paymentTypeResponse = new PaymentTypeResponse();
            paymentTypeResponse.setPaymentTypeId(paymentType.getPaymentTypeID());
            paymentTypeResponse.setPaymentTypeName(paymentType.getPaymentTypeName());
            paymentTypeResponse.setDescription(paymentType.getDescription());
            paymentTypeResponse.setFee(paymentType.getFee());
            return paymentTypeResponse;
        }).toList();

    }

    public List<ShippingMethodResponse> findAllShippingMethod() {
        return shippingMethodRepository.findAll().stream().map(shippingMethod -> {
            ShippingMethodResponse shippingMethodResponse = new ShippingMethodResponse();
            shippingMethodResponse.setShippingMethodId(shippingMethod.getShippingMethodId());
            shippingMethodResponse.setShippingMethodName(shippingMethod.getShippingMethodName());
            shippingMethodResponse.setDescription(shippingMethod.getDescription());
            shippingMethodResponse.setFee(shippingMethod.getFee());
            return shippingMethodResponse;
        }).toList();

    }
}
