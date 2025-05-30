package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.Request.RestPasswordRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.Notification;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.UserResponse;
import com.HieuVo.FashionStore_BackEnd.Model.Role;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.AdderssRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.RoleRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;
import com.HieuVo.FashionStore_BackEnd.Util.Mail.SendEmailController;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailController emailService;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       SendEmailController emailService, RoleRepository roleRepository,
                       AdderssRepository addressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.roleRepository = roleRepository;


    }

    public String registerUser(UserResponse userDTO) {
        if (this.userRepository.existsByUserName(userDTO.getUserName())) {
            return "Tài khoản đã tồn tại";
        }
        if (this.userRepository.existsByEmail(userDTO.getEmail())) {
            return "Email đã tồn tại";
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        user.setEmail(userDTO.getEmail());
        // Xử lý dữ liệu avatar
        if (userDTO.getAvatarBase64() != null && !userDTO.getAvatarBase64().isEmpty()) {
            // Chuyển đổi chuỗi base64 thành byte[] trước khi lưu
            user.setAvatarData(Base64.getDecoder().decode(userDTO.getAvatarBase64()));
        }

        user.setActivationCode(randomOTP());
        user.setActive(false);
        if (userDTO.getRoles() != null) {
            List<Role> roles = this.roleRepository.findAllByRoleNameIn(userDTO.getRoles());
        } else {
            user.setListRoles(Collections.singletonList(this.roleRepository.findByRoleName("USER")));

        }


        User newUser = this.userRepository.save(user);
        //        send email
        sendActivationEmail(user.getEmail(), user.getActivationCode());
        return "Đăng ký thành công";
    }

    private String randomOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendActivationEmail(String email, String activationCode) {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("URL");
        String mail = dotenv.get("MAIL_USERNAME");
        String subject = "Kích hoạt tài khoản";

        // Tạo email HTML với thiết kế đẹp
        String htmlMessage =
                "<!DOCTYPE html>" +
                        "<html lang='vi'>" +
                        "<head>" +
                        "<meta charset='UTF-8'>" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "<title>Kích hoạt tài khoản</title>" +
                        "<style>" +
                        "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f9f9f9; }" +
                        ".container { max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }" +
                        ".header { background-color: #4285f4; color: white; padding: 20px; text-align: center; }" +
                        ".content { padding: 30px; }" +
                        ".code { background-color: #f5f5f5; padding: 15px; border-radius: 4px; text-align: center; margin: 20px 0; font-family: monospace; font-size: 24px; letter-spacing: 4px; }" +
                        ".button { display: inline-block; background-color: #4285f4; color: white; text-decoration: none; padding: 12px 30px; border-radius: 4px; margin-top: 20px; font-weight: bold; }" +
                        ".button:hover { background-color: #3367d6; }" +
                        ".footer { text-align: center; padding: 20px; color: #777; font-size: 12px; border-top: 1px solid #eee; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='container'>" +
                        "<div class='header'>" +
                        "<h1>Xác nhận tài khoản</h1>" +
                        "</div>" +
                        "<div class='content'>" +
                        "<p>Xin chào,</p>" +
                        "<p>Cảm ơn bạn đã đăng ký tài khoản tại Fashion Store. Để hoàn tất quá trình đăng ký, vui lòng xác nhận địa chỉ email của bạn.</p>" +
                        "<p>Mã kích hoạt của bạn là:</p>" +
                        "<div class='code'>" + activationCode + "</div>" +
                        "<p>Hoặc bạn có thể nhấn vào nút bên dưới để kích hoạt tài khoản ngay lập tức:</p>" +
                        "<div style='text-align: center;'>" +
                        "<a href='" + url + "/activateAccount/" + email + "/" + activationCode + "' class='button'>Kích hoạt tài khoản</a>" +
                        "</div>" +
                        "<p style='margin-top: 30px;'>Nếu bạn không yêu cầu tạo tài khoản này, bạn có thể bỏ qua email này.</p>" +
                        "</div>" +
                        "<div class='footer'>" +
                        "<p>© 2025 Fashion Store. Tất cả các quyền được bảo lưu.</p>" +
                        "</div>" +
                        "</div>" +
                        "</body>" +
                        "</html>";

        try {
            this.emailService.sendHtmlMessage(mail, email, subject, htmlMessage);
            System.out.println("Gửi mail kích hoạt thành công");
        } catch (MessagingException e) {
            System.err.println("Lỗi gửi email kích hoạt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ResponseEntity<Notification> confirmEmail(String email, String activationCode) throws Exception {
        User user = this.userRepository.findByEmail(email);
        System.out.println("Request activation code: '" + activationCode + "'");
        System.out.println("DB activation code: '" + (user != null ? user.getActivationCode() : "null") + "'");

        if (user == null) {
            throw new Exception("Email không tồn tại");
        }
        if (user.isActive()) {
            return ResponseEntity.ok(new Notification("Tài khoản đã được kích hoạt"));
        }
        if (activationCode.trim().equals(user.getActivationCode().trim())) {
            user.setActive(true);
            this.userRepository.save(user);
            return ResponseEntity.ok(new Notification("Kích hoạt tài khoản thành công"));
        } else {
            throw new Exception("Mã kích hoạt sai!");

        }
    }


    public String restPassword(RestPasswordRequest restPasswordRequest) throws Exception {
        if (userRepository.existsByEmailAndUserName(restPasswordRequest.getEmail(), restPasswordRequest.getUserName())) {
            String code = userRepository.findByUserName(restPasswordRequest.getUserName()).get().getActivationCode();

            Dotenv dotenv = Dotenv.load();
            String frontendUrl = dotenv.get("URL") + "/rest-password" + restPasswordRequest.getEmail() + "/" + code;
            String mail = dotenv.get("MAIL_USERNAME");
            String subject = "Khôi phục mật khẩu tài khoản";

            String htmlMessage =
                    "<!DOCTYPE html>" +
                            "<html lang=\"vi\">" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                            "<title>Khôi phục mật khẩu</title>" +
                            "<style>" +
                            "body {" +
                            "font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;" +
                            "line-height: 1.6;" +
                            "color: #333333;" +
                            "background-color: #f9f9f9;" +
                            "margin: 0;" +
                            "padding: 0;" +
                            "}" +
                            ".container {" +
                            "max-width: 600px;" +
                            "margin: 0 auto;" +
                            "padding: 20px;" +
                            "}" +
                            ".email-wrapper {" +
                            "background-color: #ffffff;" +
                            "border-radius: 8px;" +
                            "box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);" +
                            "overflow: hidden;" +
                            "}" +
                            ".email-header {" +
                            "background: linear-gradient(135deg, #4F6DF5, #6C63FF);" +
                            "color: white;" +
                            "padding: 30px;" +
                            "text-align: center;" +
                            "}" +
                            ".email-header h1 {" +
                            "margin: 0;" +
                            "font-size: 24px;" +
                            "font-weight: 600;" +
                            "}" +
                            ".email-body {" +
                            "padding: 30px;" +
                            "}" +
                            ".greeting {" +
                            "font-size: 18px;" +
                            "margin-bottom: 20px;" +
                            "color: #4F6DF5;" +
                            "font-weight: 600;" +
                            "}" +
                            ".message {" +
                            "margin-bottom: 30px;" +
                            "color: #555;" +
                            "}" +
                            ".action-button {" +
                            "display: inline-block;" +
                            "background-color: #4F6DF5;" +
                            "color: white;" +
                            "text-decoration: none;" +
                            "padding: 14px 25px;" +
                            "text-align: center;" +
                            "border-radius: 8px;" +
                            "font-weight: bold;" +
                            "margin: 30px 0;" +
                            "}" +
                            ".email-footer {" +
                            "background-color: #f4f4f4;" +
                            "padding: 20px;" +
                            "text-align: center;" +
                            "font-size: 14px;" +
                            "color: #777;" +
                            "}" +
                            ".note {" +
                            "background-color: #fff8e1;" +
                            "border-left: 4px solid #ffc107;" +
                            "padding: 15px;" +
                            "margin: 20px 0;" +
                            "font-size: 14px;" +
                            "border-radius: 8px;" +
                            "}" +
                            ".divider {" +
                            "height: 1px;" +
                            "background-color: #eee;" +
                            "margin: 25px 0;" +
                            "}" +
                            "@media only screen and (max-width: 600px) {" +
                            ".container {" +
                            "padding: 10px;" +
                            "}" +
                            ".email-header, .email-body, .email-footer {" +
                            "padding: 20px;" +
                            "}" +
                            "}" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<div class=\"container\">" +
                            "<div class=\"email-wrapper\">" +
                            "<div class=\"email-header\">" +
                            "<h1>KHÔI PHỤC MẬT KHẨU</h1>" +
                            "</div>" +
                            "<div class=\"email-body\">" +
                            "<div class=\"greeting\">Xin chào " + restPasswordRequest.getUserName() + ",</div>" +
                            "<div class=\"message\">" +
                            "Chúng tôi nhận được yêu cầu khôi phục mật khẩu cho tài khoản của bạn. Vui lòng nhấp vào nút bên dưới để đặt lại mật khẩu:" +
                            "</div>" +
                            "<a href=\"" + frontendUrl + "\" class=\"action-button\">ĐẶT LẠI MẬT KHẨU</a>" +
                            "<div class=\"note\">" +
                            "<strong>Lưu ý:</strong> Liên kết này chỉ có hiệu lực trong vòng 24 giờ. Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email." +
                            "</div>" +
                            "<div class=\"divider\"></div>" +
                            "<p>Nếu bạn gặp bất kỳ vấn đề nào, vui lòng liên hệ với đội ngũ hỗ trợ của chúng tôi.</p>" +
                            "<p>Trân trọng,<br>Đội ngũ hỗ trợ khách hàng</p>" +
                            "</div>" +
                            "<div class=\"email-footer\">" +
                            "<p>&copy; 2025 Công ty của bạn. Mọi quyền được bảo lưu.</p>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "</body>" +
                            "</html>";

            try {
                this.emailService.sendHtmlMessage(mail, restPasswordRequest.getEmail(), subject, htmlMessage);
                System.out.println("Gửi mail khôi phục mật khẩu thành công");
            } catch (MessagingException e) {
                System.err.println("Lỗi gửi email khôi phục mật khẩu: " + e.getMessage());
                e.printStackTrace();
                return "Lỗi khi gửi email khôi phục mật khẩu";
            }
            return "Kiểm tra email để khôi phục mật khẩu";
        }

        throw  new Exception("Email hoặc tên đăng nhập không đúng");
    }

    public String newPassword(String email, String password,String activationCode) throws Exception {
        Optional <User> userO= this.userRepository.findByEmailAndActivationCode(email, activationCode);
        if(userO.isEmpty()) {
            throw new Exception("Email hoặc mã kích hoạt không đúng");
        }
        User user = userO.get();
        user.setPassword(passwordEncoder.encode(password));

        this.userRepository.save(user);

        return "Đặt lại mật khẩu thành công";
    }
}