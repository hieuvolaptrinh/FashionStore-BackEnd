package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.UserDTO;
import com.HieuVo.FashionStore_BackEnd.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/user")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrerNewUser(@RequestBody UserDTO userDTO) {
        ResponseEntity<?> response = this.userService.registerUser(userDTO);
        return response;
    }

    @GetMapping("/activateAccount")
    public ResponseEntity<?> confirmNewUser(@RequestParam String email, @RequestParam String activationCode) {
        return this.userService.confirmEmail(email, activationCode);
    }
}