package com.HieuVo.FashionStore_BackEnd.Config;

import com.HieuVo.FashionStore_BackEnd.Filter.JwtService;
import com.HieuVo.FashionStore_BackEnd.Model.Role;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BƯỚC 2: Handler xử lý sau khi xác thực OAuth2 thành công
 * - Được gọi sau CustomOAuth2UserService
 * - Tạo JWT token cho người dùng đã được xác thực
 * - Trả về thông tin cần thiết cho frontend (username, avatar, token)
 * - Chuyển hướng người dùng về frontend
 */
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        private static final Logger logger = LoggerFactory.getLogger(OAuth2SuccessHandler.class);
        private final JwtService jwtService;
        private final UserRepository userRepository;
        private final String frontendUrl;
        private final ObjectMapper objectMapper = new ObjectMapper();

        public OAuth2SuccessHandler(JwtService jwtService, UserRepository userRepository) {
                this.jwtService = jwtService;
                this.userRepository = userRepository;

                Dotenv dotenv = Dotenv.load();
                this.frontendUrl = dotenv.get("URL") != null ? dotenv.get("URL") : "http://localhost:5173";
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException, ServletException {

                System.out.println("OAuth2 đăng nhập thành công, đang xử lý...");
                DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
                String email = principal.getAttribute("email");

                try {
                        User user = userRepository.findByEmail(email)
                                        .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

                        // Trích xuất vai trò và tạo token
                        List<String> roles = user.getListRoles().stream()
                                        .map(Role::getRoleName)
                                        .collect(Collectors.toList());
                        String token = jwtService.generateToken(user.getUserName(), roles);

                        // In ra thông tin sẽ gửi về frontend
                        System.out.println("Thông tin gửi về frontend:");
                        System.out.println("Username: " + user.getUserName());
                        System.out.println("Avatar URL: " + user.getAvataUrl());
                        System.out.println("Token: " + token.substring(0, 20) + "...");
                        System.out.println("Roles: " + String.join(", ", roles));

                        // Chuyển đổi danh sách vai trò thành chuỗi phân cách bằng dấu phẩy
                        String rolesStr = String.join(",", roles);

                        String redirectUrl = frontendUrl + "/oauth2/redirect" +
                                        "?username="
                                        + URLEncoder.encode(user.getUserName(), StandardCharsets.UTF_8.toString()) +
                                        "&token=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString()) +
                                        "&roles=" + URLEncoder.encode(rolesStr, StandardCharsets.UTF_8.toString());

                        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
                } catch (Exception e) {
                        logger.error("Lỗi khi xử lý đăng nhập OAuth2: ", e);
                        String errorRedirect = frontendUrl + "/oauth2/error?message=" +
                                        URLEncoder.encode("Đã xảy ra lỗi khi xử lý đăng nhập Google",
                                                        StandardCharsets.UTF_8.toString());
                        getRedirectStrategy().sendRedirect(request, response, errorRedirect);
                }
        }
}