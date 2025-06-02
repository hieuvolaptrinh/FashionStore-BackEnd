package com.HieuVo.FashionStore_BackEnd.Config;

import com.HieuVo.FashionStore_BackEnd.Filter.JwtAuthenticationFilter;
import com.HieuVo.FashionStore_BackEnd.Service.CustomOAuth2UserService;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfiguration {
    Dotenv dotenv = Dotenv.load();
    private String url = dotenv.get("URL");
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    // thêm lazy để tránh lỗi khi khởi tạo bean vì nó phụ thuộc vòng tròn mất
    public SecurityConfiguration(
            @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomOAuth2UserService customOAuth2UserService,
            OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF nếu cần
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, Endpoints.PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers("/api/payment/vnpay-return", "/api/v1/oauth2/**").permitAll()
                        .requestMatchers("/login/oauth2/code/**", "/oauth2/**").permitAll() // Cho phép các endpoint
                                                                                            // OAuth2
                        .requestMatchers(Endpoints.USER_ENDPOINTS).hasAnyAuthority("USER", "ADMIN", "SHIPPER")
                        .requestMatchers(Endpoints.SHIPPER_ENDPOINTS).hasAnyAuthority("SHIPPER", "ADMIN")
                        .requestMatchers(Endpoints.ADMIN_ENDPOINTS).hasAuthority("ADMIN")
                        .anyRequest().authenticated())

                // cors
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    // Cho phép tất cả origins trong mạng local
                    config.setAllowedOriginPatterns(List.of(
                            "*"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*")); // chấp nhận tất cả các header từ request
                    config.setAllowCredentials(true);// cho phép gửi cookie, token (JWT) từ frontend
                    return config;
                }))

                .formLogin(formLogin -> formLogin.permitAll()) // cho phép login bằng form
                .httpBasic(httpBasic -> httpBasic.disable()) // tắt HTTP basic authentication

                // Cấu hình OAuth2
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .permitAll());

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // không lưu
                // session
                // về sử
                // dụng jwt
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // filter jwt

        return http.build();
    }
}