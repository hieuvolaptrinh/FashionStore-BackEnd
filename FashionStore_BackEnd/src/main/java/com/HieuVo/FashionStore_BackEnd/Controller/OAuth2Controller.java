package com.HieuVo.FashionStore_BackEnd.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Luồng xác thực OAuth2:
 * 
 * 1. Frontend gọi API: /api/v1/oauth2/google
 * 2. Backend chuyển hướng người dùng đến trang đăng nhập Google
 * 3. Google xác thực và trả về thông tin người dùng
 * 4. CustomOAuth2UserService xử lý thông tin và lưu vào database
 * 5. OAuth2SuccessHandler tạo JWT token và đóng gói thông tin
 * 6. Backend chuyển hướng về frontend với URL:
 * {frontendUrl}/oauth2/redirect?username={username}&token={token}&avatarUrl={avatarUrl}
 *
 * Thông tin trả về cho frontend:
 * - token: JWT token để xác thực các request tiếp theo
 * - username: Tên đăng nhập của người dùng
 * - avatarUrl: URL ảnh đại diện từ Google
 */
@RestController
@RequestMapping("/api/v1/oauth2")
public class OAuth2Controller {


    @GetMapping("/google")
    public void redirectToGoogleAuth(HttpServletResponse response) throws IOException {
        System.out.println("Bắt đầu quá trình đăng nhập Google OAuth2");
        response.sendRedirect("/api/v1/oauth2/authorization/google");
    }

    /**
     * Endpoint để xử lý khi có lỗi xảy ra trong quá trình OAuth2
     * Frontend có thể hiển thị thông báo lỗi cho người dùng
     */
    @GetMapping("/error")
    public String handleOAuth2Error() {
        return "Đã xảy ra lỗi trong quá trình xác thực Google OAuth2. Vui lòng thử lại sau.";
    }
}