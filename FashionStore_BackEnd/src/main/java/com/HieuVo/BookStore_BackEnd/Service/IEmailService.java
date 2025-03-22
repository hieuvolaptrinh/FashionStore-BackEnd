package com.HieuVo.BookStore_BackEnd.Service;

public interface IEmailService {
    void sendMessage(String from, String to, String subject, String message);

}
