package com.HieuVo.FashionStore_BackEnd.Util.Mail;

import jakarta.mail.MessagingException;

public interface IEmail {
    void sendMessage(String from, String to, String subject, String plainText);
    void sendHtmlMessage(String from, String to, String subject, String htmlContent) throws MessagingException, MessagingException;
}