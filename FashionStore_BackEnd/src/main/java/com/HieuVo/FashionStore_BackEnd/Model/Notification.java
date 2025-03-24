package com.HieuVo.FashionStore_BackEnd.Model;

import lombok.Data;

@Data
public class Notification {
    private String message;

    public Notification(String message) {
        this.message = message;
    }

}
