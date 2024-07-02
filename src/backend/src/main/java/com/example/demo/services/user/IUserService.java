package com.example.demo.services.user;

import com.example.demo.dtos.userDTOS.*;
import com.example.demo.models.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<User> findAllUser(String keyword, Pageable pageable) throws Exception;
    User createUser(UserRegisterDTO userRegisterDTO) throws Exception;
    String login(UserLoginDTO userLoginDTO) throws Exception;
    User getUserDetailFromPhoneNumberAndEmail(UserForgetPasswordDTO userForgetPasswordDTO) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User updateUserDetail(String userId, UserUpdateDTO userUpdateDTO) throws Exception;
    User resetPassword(String userId, UserResetPasswordDTO userResetPasswordDTO) throws Exception;
    void delete(String userId) throws Exception;
}
