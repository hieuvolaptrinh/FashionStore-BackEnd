package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.UserDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.Notification;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/user")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/{username}/avatar")
    public ResponseEntity<String> getAvatar(@PathVariable String username) {
        User user = userService.fetchUserByUsername(username);
        if (user == null || user.getAvatarData() == null) {
            return ResponseEntity.ok(null);
        }
        byte[] avatarBytes = user.getAvatarData();
        String base64Image = Base64.getEncoder().encodeToString(avatarBytes);
        return ResponseEntity.ok(base64Image);
    }


//    CRUD admin
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    @ApiMessage("Cập nhật người dùng thành công")
    public ResponseEntity<Notification> updateUser(@PathVariable Integer userId, @RequestBody UserDTO userDTO) {
        System.out.println("Received userDTO: " + userDTO);
        return ResponseEntity.ok(new Notification(this.userService.updateUser(userId, userDTO)) );
    }

    @PutMapping("/lock/{userId}")
    @ApiMessage("Khóa tài khoản thành công")
    public ResponseEntity<Notification> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(new Notification(this.userService.lockAccount(userId)));
    }
}