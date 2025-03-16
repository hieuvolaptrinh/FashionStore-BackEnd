package com.HieuVo.BookStore_BackEnd.Controller;

import com.HieuVo.BookStore_BackEnd.DTO.UserDTO;
import com.HieuVo.BookStore_BackEnd.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/exists")
    public ResponseEntity<?> checkUserExists(@RequestBody UserDTO userDTO) {
        ResponseEntity<?> response = this.userService.registerUser(userDTO);
        return response;
    }
}