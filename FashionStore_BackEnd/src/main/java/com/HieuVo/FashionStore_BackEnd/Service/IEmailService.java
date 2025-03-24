package com.HieuVo.FashionStore_BackEnd.Service;

import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendMessage(String from, String to, String subject, String plainText);
    void sendHtmlMessage(String from, String to, String subject, String htmlContent) throws MessagingException, MessagingException;
}