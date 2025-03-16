package com.HieuVo.BookStore_BackEnd.Service;

import com.HieuVo.BookStore_BackEnd.DTO.UserDTO;
import com.HieuVo.BookStore_BackEnd.Model.Notification;
import com.HieuVo.BookStore_BackEnd.Model.User;
import com.HieuVo.BookStore_BackEnd.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public ResponseEntity<?> registerUser(UserDTO userDTO) {

        if(this.userRepository.existsByUserName(userDTO.getUserName())) {
            return ResponseEntity.badRequest().body(new Notification("Tài khoản đã tồn tại"));
        }
        if(this.userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new Notification("Email đã tồn tại"));
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.getPhoneNumber(userDTO.getPhoneNumber());

        user.setEmail(userDTO.getEmail());
        User newUser = this.userRepository.save(user);
        return ResponseEntity.ok("Đăng ký thành công");
    }
}