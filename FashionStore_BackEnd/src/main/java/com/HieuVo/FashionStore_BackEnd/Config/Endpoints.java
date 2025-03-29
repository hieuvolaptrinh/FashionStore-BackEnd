package com.HieuVo.FashionStore_BackEnd.Config;

public class Endpoints {
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/products/**",
            "/users/**",
            "/types/**",
            "/review-list/**",
            "/api/v1/products/**",
            "/api/v1/user/**",
            "/api/v1/roles/**",
            "/api/v1/review-list/**"};
    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/v1/user/register",
            "/api/v1/users/login",
            "/api/v1/review-list/**",
            "/api/v1/orders/**",
            "/api/v1/auth/login"};

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/products/**",
            "/api/v1/products/**"
    };
}
