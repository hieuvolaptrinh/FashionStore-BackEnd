package com.HieuVo.FashionStore_BackEnd;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookStoreBackEndApplication {

    public static void main(String[] args) {
        // Khởi tạo dotenv và tải file .env
        Dotenv dotenv = Dotenv.load();

        // Đặt các biến môi trường trong hệ thống
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
        // payment
        System.setProperty("VNPAY_TMN_CODE", dotenv.get("VNPAY_TMN_CODE"));
        System.setProperty("VNPAY_HASH_SECRET", dotenv.get("VNPAY_HASH_SECRET"));
        System.setProperty("VNPAY_VNP_RETURNURL", dotenv.get("VNPAY_VNP_RETURNURL"));

        SpringApplication.run(BookStoreBackEndApplication.class, args);
    }

}
