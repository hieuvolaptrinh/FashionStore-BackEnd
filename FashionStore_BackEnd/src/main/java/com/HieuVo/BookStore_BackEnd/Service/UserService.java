package com.HieuVo.BookStore_BackEnd.Service;

import com.HieuVo.BookStore_BackEnd.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}