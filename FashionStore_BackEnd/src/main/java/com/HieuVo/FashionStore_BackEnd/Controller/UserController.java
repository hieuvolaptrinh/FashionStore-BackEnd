package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.UserDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Notification;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/user")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrerNewUser(@RequestBody UserDTO userDTO) {
        ResponseEntity<String> response = this.userService.registerUser(userDTO);
        return response;
    }


    @GetMapping("/{username}/avatar")
    public ResponseEntity<String> getAvatar(@PathVariable String username) {
        User user = userService.fetchUserByUsername(username);
        if (user==null || user.getAvatarData() == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] avatarBytes = user.getAvatarData();
        String base64Image = Base64.getEncoder().encodeToString(avatarBytes);

        return ResponseEntity.ok(base64Image);
    }

    @GetMapping("/activateAccount")
    public ResponseEntity<Notification> confirmNewUser(@RequestParam String email, @RequestParam String activationCode) {
        return this.userService.confirmEmail(email, activationCode);
    }
}