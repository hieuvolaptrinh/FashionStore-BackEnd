package com.HieuVo.FashionStore_BackEnd.Config;

import com.HieuVo.FashionStore_BackEnd.Filter.JwtAuthenticationFilter;
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

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    thêm lazy để tránh lỗi khi khởi tạo bean vì nó phụ thuộc vòng tròn mất
    public SecurityConfiguration(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

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
                .csrf(csrf -> csrf.disable()) // Tắt CSRF nếu cần
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/**").permitAll() // ← Cái này QUAN TRỌNG!
                        .requestMatchers(HttpMethod.POST, Endpoints.PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, Endpoints.ADMIN_POST_ENDPOINTS).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, Endpoints.ADMIN_POST_ENDPOINTS).hasAuthority("ADMIN")
                        .requestMatchers("/**").permitAll() // Tạm thời thôi để test
//                        .requestMatchers("/users/**").hasAnyAuthority("USER","ADMIN")
                        .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(url)); //chỉ cho phép các request từ url của frontend
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*")); //	chấp nhận tất cả các header từ request
                    config.setAllowCredentials(true);//	cho phép gửi cookie, token (JWT) từ frontend
                    return config;
                }))
                .formLogin(Customizer.withDefaults()) // cho phép login bằng form (chỉ dùng nếu dùng session hoặc dev đang test).
                .httpBasic(Customizer.withDefaults()); //login bằng HTTP basic (có thể tắt nếu  chỉ dùng JWT)
        http.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // không lưu session về sử dụng jwt
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); //filter jwt trước filter mặc định

        return http.build();
    }


}