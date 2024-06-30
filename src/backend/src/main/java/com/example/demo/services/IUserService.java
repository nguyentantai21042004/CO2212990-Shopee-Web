package com.example.demo.services;

import com.example.demo.dtos.UserLoginDTO;
import com.example.demo.dtos.UserRegisterDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.models.users.User;
import com.example.demo.repositories.UserRepository;

public interface IUserService {
    User createUser(UserRegisterDTO userRegisterDTO) throws DataNotFoundException;
    String login(UserLoginDTO userLoginDTO) throws Exception;
}
