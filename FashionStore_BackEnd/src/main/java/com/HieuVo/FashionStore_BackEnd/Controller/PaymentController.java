package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.Request.PaymentRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.Notification;
import com.HieuVo.FashionStore_BackEnd.Service.OrderService;
import com.HieuVo.FashionStore_BackEnd.Service.VNPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final VNPayService vnPayService;
    private final OrderService orderService;

    public PaymentController(VNPayService vnPayService, OrderService orderService) {
        this.vnPayService = vnPayService;
        this.orderService = orderService;
    }

    @PostMapping("/create-payment")
    public ResponseEntity<Notification> createPayment(@RequestBody PaymentRequest paymentDTO) {
        try {
            String paymentUrl = vnPayService.createPaymentUrl(paymentDTO);
            return ResponseEntity.ok(new Notification(paymentUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Notification("Error creating payment URL: " + e.getMessage()));
        }
    }
    // Request Body
    // {
    // "orderId": 1, // Mã đơn hàng của bạn
    // "amount": 100000, // Số tiền thanh toán (VNĐ)
    // "orderInfo": "Thanh toan don hang", // Thông tin đơn hàng
    // "bankCode": "NCB", // Mã ngân hàng (tùy chọn)
    // "language": "vn", // Ngôn ngữ (vn/en)
    // "ipAddress": "127.0.0.1" // IP của người dùng
    // }
    //

    @GetMapping("/vnpay-return")
    public ResponseEntity<Map<String, Object>> vnpayReturn(@RequestParam Map<String, String> allParams)
            throws UnsupportedEncodingException {
        Map<String, Object> result = vnPayService.verifyPayment(allParams);

        if ((Boolean) result.get("isValid")) {
            String responseCode = (String) result.get("responseCode");
            if ("00".equals(responseCode)) {
                // Thanh toán thành công
                String orderId = (String) result.get("orderId");
                // Cập nhật trạng thái thanh toán của đơn hàng
                orderService.updatePaymentStatus(Integer.parseInt(orderId), true);

                result.put("message", "Thanh toán thành công");
                result.put("isPay", true);
                return ResponseEntity.ok(result);
            } else {
                // Thanh toán thất bại
                result.put("message", "Thanh toán thất bại");
                result.put("isPay", false);
                return ResponseEntity.ok(result);
            }
        } else {
            // Lỗi xác thực
            result.put("message", "Lỗi xác thực thanh toán");
            result.put("isPay", false);
            return ResponseEntity.ok(result);
        }
    }
    // // Query Parameters từ VNPAY
    // {
    // "vnp_Amount": "10000000",
    // "vnp_BankCode": "NCB",
    // "vnp_BankTranNo": "VNP12345678",
    // "vnp_CardType": "ATM",
    // "vnp_OrderInfo": "Thanh toan don hang",
    // "vnp_PayDate": "20240315163000",
    // "vnp_ResponseCode": "00",
    // "vnp_TmnCode": "DBHAXRU3",
    // "vnp_TransactionNo": "12345678",
    // "vnp_TransactionStatus": "00",
    // "vnp_TxnRef": "ORDER_123",
    // "vnp_SecureHash": "..."
    // }

    @PostMapping("/vnpay-ipn")
    public ResponseEntity<Map<String, Object>> vnpayIpn(@RequestParam Map<String, String> allParams)
            throws UnsupportedEncodingException {
        Map<String, Object> result = vnPayService.verifyPayment(allParams);

        if ((Boolean) result.get("isValid")) {
            String responseCode = (String) result.get("responseCode");
            if ("00".equals(responseCode)) {
                // Xử lý IPN thành công
                String orderId = (String) result.get("orderId");
                // Cập nhật trạng thái thanh toán của đơn hàng
                orderService.updatePaymentStatus(Integer.parseInt(orderId), true);

                result.put("message", "IPN processed successfully");
                result.put("isPay", true);
                return ResponseEntity.ok(result);
            } else {
                // Xử lý IPN thất bại
                result.put("message", "IPN processing failed");
                result.put("isPay", false);
                return ResponseEntity.ok(result);
            }
        } else {
            // Lỗi xác thực IPN
            result.put("message", "Invalid IPN");
            result.put("isPay", false);
            return ResponseEntity.ok(result);
        }
    }

}
