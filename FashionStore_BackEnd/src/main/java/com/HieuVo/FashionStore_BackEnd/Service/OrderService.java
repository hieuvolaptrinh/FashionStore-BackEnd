package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.Request.OrderRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.PaymentTypeResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.OrderResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ShippingMethodResponse;
import com.HieuVo.FashionStore_BackEnd.Model.*;
import com.HieuVo.FashionStore_BackEnd.Repository.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final ShippingMethodRepository shippingMethodRepository;
    private final CartDetailRepository cartDetailRepository;
    private final AdderssRepository addressRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;


    public OrderService(OrderRepository orderRepository, PaymentTypeRepository paymentTypeRepository,
                        ShippingMethodRepository shippingMethodRepository,
                        CartDetailRepository cartDetailRepository,
                        AdderssRepository addressRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository,
                        ImageRepository imageRepository) {
        this.orderRepository = orderRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.shippingMethodRepository = shippingMethodRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
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

    @Transactional
    public void createOrder(UserDetails userDetails, OrderRequest orderRequest) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        PaymentType paymentType = paymentTypeRepository.findById(orderRequest.getPaymentTypeId())
                .orElseThrow(() -> new RuntimeException("Payment type not found"));

        ShippingMethod shippingMethod = shippingMethodRepository.findById(orderRequest.getShippingMethodId())
                .orElseThrow(() -> new RuntimeException("Shipping method not found"));

        Optional<List<CartDetail>> optinalCartDetails = cartDetailRepository.findByCartDetailIdIn(orderRequest.getSelectedIds());
        List<CartDetail> cartDetails = optinalCartDetails.orElseThrow(() -> new RuntimeException("No cart items found"));
        if (cartDetails.isEmpty()) {
            throw new RuntimeException("No cart details found");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setPaymentType(paymentType);
        order.setShippingMethod(shippingMethod);
//        order.setCreateAt(Date.valueOf(LocalDate.now()));
//        order.setStatus(" chờ đại ka hiếu Xử lý/ trừ trước luôn số lượng sản phẩm ở code hoặc code trigger cx đc");
        order.setStatus("Chưa xử lý");

        double totalPrice = cartDetails.stream()
                .mapToDouble(cd -> cd.getPrice() * cd.getQuantity())
                .sum();
        totalPrice += paymentType.getFee() + shippingMethod.getFee();
        order.setTotalPrice(totalPrice);

        List<OrderDetail> orderDetails = cartDetails.stream().map(cd -> {
            OrderDetail od = new OrderDetail();
            od.setOrder(order);
            od.setProduct(cd.getProduct());
            od.setQuantity(cd.getQuantity());
            od.setPrice(cd.getPrice());
            return od;
        }).collect(Collectors.toList());
//        giảm số lượng sản phẩm trong kho
//        for (OrderDetail orderDetail : orderDetails) {
//            Product product = orderDetail.getProduct();
//            product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
//            this.productRepository.save(product);
//        }


        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        // Xóa cart details
        cartDetailRepository.deleteAll(cartDetails);
    }


    public List<OrderResponse> getAllOrdersByUser(UserDetails userDetails) {
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Order> orders = orderRepository.findByUser(user)
                .orElse(Collections.emptyList());

        return orders.stream()
                .map(this::convertToResponseOrder)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToResponseOrder)
                .collect(Collectors.toList());
    }

    private OrderResponse convertToResponseOrder(Order order) {
        OrderResponse responseOrder = new OrderResponse();
        responseOrder.setOrderId(order.getOrderId());
        responseOrder.setStatus(order.getStatus());
        responseOrder.setTotalPrice(order.getTotalPrice());
        responseOrder.setCreateAt(order.getCreateAt());
        List<OrderResponse.OrderDetailDTO> orderDetails = new ArrayList<>();

        for(OrderDetail detail : order.getOrderDetails()) {
            OrderResponse.OrderDetailDTO orderDetailDTO = new OrderResponse.OrderDetailDTO();
            orderDetailDTO.setOrderDetailId(detail.getOrderDetailId());
            orderDetailDTO.setQuantity(detail.getQuantity());
            orderDetailDTO.setPrice(detail.getPrice());
            orderDetailDTO.setProductName(detail.getProduct().getProductName());
            orderDetailDTO.setDescription(detail.getProduct().getDescription());



            List<Image> images = this.imageRepository.findByProduct_productId(detail.getOrderDetailId());
            for(Image image : images) {
                if(image.isIcon()==true) {
                    orderDetailDTO.setMainImage(image.getLink());
                }
            }
            if(orderDetailDTO.getMainImage()==null) {
                orderDetailDTO.setMainImage(images.get(0).getLink());
            }
            orderDetails.add(orderDetailDTO);

        }


        responseOrder.setOrderDetails(orderDetails);
        return responseOrder;
    }

    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}

