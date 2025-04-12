package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.OrderDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.PaymentTypeResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ShippingMethodResponse;
import com.HieuVo.FashionStore_BackEnd.Model.*;
import com.HieuVo.FashionStore_BackEnd.Repository.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
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

    public OrderService(OrderRepository orderRepository, PaymentTypeRepository paymentTypeRepository,
                        ShippingMethodRepository shippingMethodRepository,
                        CartDetailRepository cartDetailRepository,
                        AdderssRepository addressRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.shippingMethodRepository = shippingMethodRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
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
    public void createOrder(UserDetails userDetails, OrderDTO orderDTO) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        PaymentType paymentType = paymentTypeRepository.findById(orderDTO.getPaymentTypeId())
                .orElseThrow(() -> new RuntimeException("Payment type not found"));

        ShippingMethod shippingMethod = shippingMethodRepository.findById(orderDTO.getShippingMethodId())
                .orElseThrow(() -> new RuntimeException("Shipping method not found"));

        List<CartDetail> cartDetails = cartDetailRepository.findByCartDetailIdIn(orderDTO.getSelectedIds());
        if (cartDetails.isEmpty()) {
            throw new RuntimeException("No cart details found");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setPaymentType(paymentType);
        order.setShippingMethod(shippingMethod);
        order.setCreateAt(Date.valueOf(LocalDate.now()));
        order.setStatus(" chờ đại ka hiếu Xử lý/ trừ trước luôn số lượng sản phẩm ở code hoặc code trigger cx đc");

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

}

