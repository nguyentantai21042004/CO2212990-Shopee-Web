package com.example.demo.services;

import com.example.demo.models.users.User;
import com.example.demo.repositories.UserRepository;

import javax.mail.MessagingException;

public interface IUserService {
    public User addUser();

//    public void sendEmail(String to, String subject, String text) throws MessagingException;
}
