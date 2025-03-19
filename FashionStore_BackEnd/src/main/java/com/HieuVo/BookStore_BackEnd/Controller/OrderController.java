package com.HieuVo.BookStore_BackEnd.Controller;


import com.HieuVo.BookStore_BackEnd.Service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @GetMapping("/orders")
//    public String getOrders() {
//        return orderService.getOrders();
//    }
}
