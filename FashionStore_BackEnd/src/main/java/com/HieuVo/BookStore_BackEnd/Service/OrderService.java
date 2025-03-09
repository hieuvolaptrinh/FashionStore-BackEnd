package com.HieuVo.BookStore_BackEnd.Service;

import com.HieuVo.BookStore_BackEnd.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
