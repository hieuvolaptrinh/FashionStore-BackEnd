package com.HieuVo.BookStore_BackEnd.Controller;

import com.HieuVo.BookStore_BackEnd.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/exists")
    public ResponseEntity<?> checkUserExists(@RequestParam(required = false) String userName) {

        if (userName != null && userService.existsByUserName(userName)) {
            return ResponseEntity.ok("Tên người dùng đã tồn tại");
        }
        return ResponseEntity.ok("Tài khoản chưa tồn tại");
    }
}