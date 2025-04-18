package com.HieuVo.FashionStore_BackEnd.DTO.Response;

import lombok.Data;

@Data
public class Notification {
    private String message;

    public Notification(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return  message ;
    }
}
