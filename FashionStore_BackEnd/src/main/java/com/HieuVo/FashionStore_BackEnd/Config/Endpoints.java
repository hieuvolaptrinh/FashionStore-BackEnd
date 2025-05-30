package com.HieuVo.FashionStore_BackEnd.Config;

public class Endpoints {
        public static final String[] PUBLIC_GET_ENDPOINTS = {

                        "/api/v1/products/**",
                        "/api/v1/products",
                        "/api/v1/auth/activateAccount",
                        "/api/v1/review-list/**",
                        "/api/v1/roles/**",
                        "/api/v1/user/*/avatar",
                        "/api/payment/vnpay-return",
                        "/api/v1/review-list/**",
                        // swagger
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
        };
        public static final String[] PUBLIC_POST_ENDPOINTS = {
                        "/api/v1/auth/register",
                        "/api/v1/review-list/**",
                        "/api/v1/orders/**",
                        "/api/v1/auth/login",
                        "/api/v1/auth/reset-password",
                        "/api/payment/**", // test
                        "/api/v1/auth/new-password"
        };
        public static final String[] USER_ENDPOINTS = {
                        "/api/v1/cart/**",
                        "/api/v1/user/*",
                "/api/v1/user/{id}"
        };

        public static final String[] SHIPPER_ENDPOINTS = {
                        "/api/v1/products/**",
                        "/api/v1/orders/admin/**",
                        "/api/v1/user/*",
                "/api/v1/user/{id}"
        };
        public static final String[] ADMIN_ENDPOINTS = {
                        "/uploadToGoogleDrive/**",
                        "/api/v1/products/**",
                        "/api/v1/user/**",
                        "/api/v1/orders/admin/**"
        };
}
