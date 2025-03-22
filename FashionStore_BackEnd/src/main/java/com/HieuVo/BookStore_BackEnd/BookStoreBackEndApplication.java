package com.HieuVo.BookStore_BackEnd;
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

		// Chạy ứng dụng Spring Boot
		SpringApplication.run(BookStoreBackEndApplication.class, args);
	}

}
