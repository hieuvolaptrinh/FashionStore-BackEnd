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

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfiguration {
        Dotenv dotenv = Dotenv.load();

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final CustomOAuth2UserService customOAuth2UserService;
        private final OAuth2SuccessHandler oAuth2SuccessHandler;
        private final PasswordEncoderConfig passwordEncoder;

        public SecurityConfiguration(
                        @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
                        CustomOAuth2UserService customOAuth2UserService,
                        OAuth2SuccessHandler oAuth2SuccessHandler,
                        PasswordEncoderConfig passwordEncoder) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                this.customOAuth2UserService = customOAuth2UserService;
                this.oAuth2SuccessHandler = oAuth2SuccessHandler;
                this.passwordEncoder = passwordEncoder;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider(UserService userService) {
                DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
                daoAuthenticationProvider.setUserDetailsService(userService);
                daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());
                return daoAuthenticationProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Tắt CSRF nếu cần
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(Endpoints.OAUTH2_ENDPOINTS).permitAll() // Sử dụng mảng
                                                                                                         // mới

                                                .requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET_ENDPOINTS)
                                                .permitAll()
                                                .requestMatchers(HttpMethod.POST, Endpoints.PUBLIC_POST_ENDPOINTS)
                                                .permitAll()
                                                .requestMatchers("/api/payment/vnpay-return").permitAll()
                                                .requestMatchers(Endpoints.USER_ENDPOINTS)
                                                .hasAnyAuthority("USER", "ADMIN", "SHIPPER")
                                                .requestMatchers(Endpoints.SHIPPER_ENDPOINTS)
                                                .hasAnyAuthority("SHIPPER", "ADMIN")
                                                .requestMatchers(Endpoints.ADMIN_ENDPOINTS).hasAuthority("ADMIN")
                                                .anyRequest().authenticated())

                                // cors
                                .cors(cors -> cors.configurationSource(request -> {
                                        CorsConfiguration config = new CorsConfiguration();
                                        // Cho phép tất cả origins trong mạng local
                                        config.setAllowedOriginPatterns(List.of(
                                                        "*"));
                                        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                                        config.setAllowedHeaders(List.of("*")); // chấp nhận tất cả các header từ
                                                                                // request
                                        config.setAllowCredentials(true);// cho phép gửi cookie, token (JWT) từ frontend
                                        return config;
                                }))

                                .formLogin(formLogin -> formLogin.disable()) // rest API không sử dụng form login
                                .httpBasic(httpBasic -> httpBasic.disable()) // tắt HTTP basic authentication

                                // Cấu hình OAuth2
                                .oauth2Login(oauth2 -> oauth2
                                                .authorizationEndpoint(authEndpoint -> authEndpoint
                                                                .baseUri("/api/v1/oauth2/authorization"))
                                                .redirectionEndpoint(redirectEndpoint -> redirectEndpoint
                                                                .baseUri("/login/oauth2/code/*"))
                                                .userInfoEndpoint(userInfo -> userInfo
                                                                .userService(customOAuth2UserService))
                                                .successHandler(oAuth2SuccessHandler));

                // Sử dụng SessionCreationPolicy.IF_REQUIRED để cho phép OAuth2 hoạt động
                http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}