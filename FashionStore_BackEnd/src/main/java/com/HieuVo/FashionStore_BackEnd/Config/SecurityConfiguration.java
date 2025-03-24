package com.HieuVo.FashionStore_BackEnd.Config;

import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    Dotenv dotenv = Dotenv.load();
    private String url = dotenv.get("URL");
//    private String url = "http://localhost:5173";


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }


// Bỏ phương thức userDetailsService() đi

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(url)); //chỉ cho phép các request từ url của frontend
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*")); //	chấp nhận tất cả các header từ request
                    config.setAllowCredentials(true);//	cho phép gửi cookie, token (JWT) từ frontend
                    return config;
                }))
                .csrf(csrf -> csrf.disable()) // Nếu cần tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, Endpoints.PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, Endpoints.ADMIN_POST_ENDPOINTS).hasAuthority("ADMIN")

//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/users/**").hasAnyAuthority("USER","ADMIN")
//                        .requestMatchers( "/types/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()) // Bật form login
                .httpBasic(Customizer.withDefaults()); // Bật HTTP Basic Auth
//

        return http.build();
    }


}