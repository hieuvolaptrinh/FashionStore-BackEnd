package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.Config.VNPayConfig;
import com.HieuVo.FashionStore_BackEnd.DTO.PaymentDTO;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {

    private final VNPayConfig vnPayConfig;

    public VNPayService(VNPayConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }

    public String createPaymentUrl(PaymentDTO paymentDTO) throws Exception {
        String vnp_TmnCode = vnPayConfig.getVnp_TmnCode();
        String vnp_HashSecret = vnPayConfig.getVnp_HashSecret();
        String vnp_Url = vnPayConfig.getVnp_Url();
        String vnp_ReturnUrl = vnPayConfig.getVnp_ReturnUrl();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(paymentDTO.getAmount() * 100)); // Số tiền * 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(paymentDTO.getOrderId())); // Mã đơn hàng
        vnp_Params.put("vnp_OrderInfo", paymentDTO.getOrderInfo());
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", paymentDTO.getLanguage() != null ? paymentDTO.getLanguage() : "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", paymentDTO.getIpAddress());

        if (paymentDTO.getBankCode() != null && !paymentDTO.getBankCode().isEmpty()) {
            vnp_Params.put("vnp_BankCode", paymentDTO.getBankCode());
        }

        // Xử lý ngày giờ
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Sắp xếp và tạo chuỗi hash
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return vnp_Url + "?" + queryUrl;
    }

    private String hmacSHA512(String key, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(data.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString().toLowerCase();
    }

    public Map<String, Object> verifyPayment(Map<String, String> vnp_Params) {
        Map<String, Object> result = new HashMap<>();
        String vnp_SecureHash = vnp_Params.get("vnp_SecureHash");
        vnp_Params.remove("vnp_SecureHash");
        vnp_Params.remove("vnp_SecureHashType");

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
            }
        }

        try {
            String vnp_HashSecret = vnPayConfig.getVnp_HashSecret();
            String calculatedHash = hmacSHA512(vnp_HashSecret, hashData.toString());
            boolean isValid = vnp_SecureHash.equals(calculatedHash);

            result.put("isValid", isValid);
            result.put("responseCode", vnp_Params.get("vnp_ResponseCode"));
            result.put("transactionNo", vnp_Params.get("vnp_TransactionNo"));
            result.put("amount", vnp_Params.get("vnp_Amount"));
            result.put("orderId", vnp_Params.get("vnp_TxnRef"));
            result.put("bankCode", vnp_Params.get("vnp_BankCode"));
            result.put("payDate", vnp_Params.get("vnp_PayDate"));

            return result;
        } catch (Exception e) {
            result.put("isValid", false);
            result.put("error", e.getMessage());
            return result;
        }
    }
}