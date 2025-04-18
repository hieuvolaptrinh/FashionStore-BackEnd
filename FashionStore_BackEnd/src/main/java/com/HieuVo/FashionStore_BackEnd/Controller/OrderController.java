package com.HieuVo.FashionStore_BackEnd.Controller;


import com.HieuVo.FashionStore_BackEnd.DTO.AddressDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.Notification;
import com.HieuVo.FashionStore_BackEnd.DTO.Request.OrderRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.PaymentTypeResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ResponseOrder;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ShippingMethodResponse;
import com.HieuVo.FashionStore_BackEnd.Model.Address;
import com.HieuVo.FashionStore_BackEnd.Service.AddressService;
import com.HieuVo.FashionStore_BackEnd.Service.OrderService;
import com.HieuVo.FashionStore_BackEnd.Service.ProductService;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
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
                                            @RequestBody OrderRequest orderRequest) {
        orderService.createOrder(userDetails, orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //
    @GetMapping("/admin")
    @ApiMessage("Get all orders successfully")
    public ResponseEntity<List<ResponseOrder>> getAllOrders() {
        List<ResponseOrder> responseOrders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(responseOrders);
    }

    @GetMapping("/user")
    @ApiMessage("Get all orders successfully")
    public ResponseEntity<List<ResponseOrder>> getAllOrdersByUser(@AuthenticationPrincipal UserDetails userDetails) {
        List<ResponseOrder> responseOrders = orderService.getAllOrdersByUser(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseOrders);
    }
//    admin update trạng thái đơn hàng
    @PostMapping("/admin/update")
    @ApiMessage("Update order status successfully")
    public ResponseEntity<Void> updateOrderStatus(@RequestParam(name = "orderId") int orderId,
                                            @RequestParam(name = "status") String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
