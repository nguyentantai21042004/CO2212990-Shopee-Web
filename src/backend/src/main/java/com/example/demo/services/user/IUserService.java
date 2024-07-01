package com.example.demo.services.user;

import com.example.demo.dtos.UserLoginDTO;
import com.example.demo.dtos.UserRegisterDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.exceptions.InvalidParamException;
import com.example.demo.models.users.User;
import com.example.demo.repositories.UserRepository;

public interface IUserService {
    User createUser(UserRegisterDTO userRegisterDTO) throws Exception;
    String login(UserLoginDTO userLoginDTO) throws Exception;
}
