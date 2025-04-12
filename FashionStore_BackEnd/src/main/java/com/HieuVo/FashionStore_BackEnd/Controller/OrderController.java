package com.HieuVo.FashionStore_BackEnd.Controller;


import com.HieuVo.FashionStore_BackEnd.DTO.AddressDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Notification;
import com.HieuVo.FashionStore_BackEnd.DTO.OrderDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.PaymentTypeResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ShippingMethodResponse;
import com.HieuVo.FashionStore_BackEnd.Model.Address;
import com.HieuVo.FashionStore_BackEnd.Model.PaymentType;
import com.HieuVo.FashionStore_BackEnd.Repository.PaymentTypeRepository;
import com.HieuVo.FashionStore_BackEnd.Service.AddressService;
import com.HieuVo.FashionStore_BackEnd.Service.OrderService;
import com.HieuVo.FashionStore_BackEnd.Service.ProductService;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final AddressService addressService;


    public OrderController(OrderService orderService, ProductService productService
            , AddressService addressService) {
        this.orderService = orderService;
        this.productService = productService;

        this.addressService = addressService;
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAddress(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(this.addressService.getAddressByUser(userDetails));
    }

    @PostMapping("/address")
    @ApiMessage("Add new address successfully")
    public ResponseEntity<Notification> createAddress(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody Address address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Notification(this.addressService.addAddressByUser(userDetails, address)));
    }

    @GetMapping("/payment-types")
    public ResponseEntity<List<PaymentTypeResponse>> getPaymentTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(this.orderService.findAllPaymentType());
    }

    @GetMapping("/shipping-methods")
    public ResponseEntity<List<ShippingMethodResponse>> getShippingMethods() {
        return ResponseEntity.status(HttpStatus.OK).body(this.orderService.findAllShippingMethod());
    }

    @PostMapping
    @ApiMessage("Create order successfully")
    public ResponseEntity<Void> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody OrderDTO orderDTO) {
        orderService.createOrder(userDetails, orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
