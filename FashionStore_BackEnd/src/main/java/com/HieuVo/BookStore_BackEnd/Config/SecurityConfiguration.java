package com.HieuVo.BookStore_BackEnd.Config;

import com.HieuVo.BookStore_BackEnd.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    private String url = "http://localhost:5173";

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        System.out.println("Creating in-memory user: admin");
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("password"))
//                .authorities("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(admin);
//    }
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

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
                    config.setAllowedOrigins(List.of(url)); //Chỉ cho phép các request từ url của frontend. Ví dụ: "http://localhost:3000"
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*")); //	Chấp nhận tất cả các header từ request
                    config.setAllowCredentials(true);//	Cho phép gửi cookie, token (JWT) từ frontend
                    return config;
                }))
                .csrf(csrf -> csrf.disable()) // Nếu cần tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Chỉ ADMIN mới truy cập được
                        .requestMatchers("/users/**").hasAnyAuthority("USER","ADMIN")
                        .requestMatchers( "/products/**").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/test/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults()) // Bật form login
                .httpBasic(Customizer.withDefaults()); // Bật HTTP Basic Auth
//                        .anyRequest().permitAll()


        //tạm thời vô hiệu hóa để test
//        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .csrf(csrf -> csrf.disable()) // Tắt CSRF (nếu cần)
//                .formLogin(AbstractHttpConfigurer::disable) // Tắt form login
//                .httpBasic(AbstractHttpConfigurer::disable); // Tắt HTTP Basic Auth
        return http.build();
    }


}