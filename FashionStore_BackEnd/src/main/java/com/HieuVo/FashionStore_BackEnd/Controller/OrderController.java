package com.HieuVo.FashionStore_BackEnd.Controller;


import com.HieuVo.FashionStore_BackEnd.Service.OrderService;
import com.HieuVo.FashionStore_BackEnd.Service.ProductService;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    public OrderController(OrderService orderService, ProductService productService, UserService userService) {
    this.orderService = orderService;
    this.productService = productService;
    this.userService = userService;
    }

    @GetMapping("/adddress")
    public ResponseEntity<?> getAddress() {
        return ResponseEntity.ok(userService.getAddress());
    }






}
