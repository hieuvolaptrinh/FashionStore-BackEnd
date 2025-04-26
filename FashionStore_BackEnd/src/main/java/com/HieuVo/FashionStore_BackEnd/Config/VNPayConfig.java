package com.HieuVo.FashionStore_BackEnd.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class VNPayConfig {
    @Value("${vnpay.vnp_TmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.vnp_HashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.vnp_Url}")
    private String vnp_Url;

    @Value("${vnpay.vnp_ReturnUrl}")
    private String vnp_ReturnUrl;

    @Value("${vnpay.vnp_IpnUrl}")
    private String vnp_IpnUrl;

}
