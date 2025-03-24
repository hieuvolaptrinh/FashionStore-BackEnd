package com.HieuVo.FashionStore_BackEnd.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/test")
    public String test() {
        return "Không cần xác thực";
    }

    @GetMapping("/users/ok")
    public String users() {
        return "Cần xác thực";
    }
}
