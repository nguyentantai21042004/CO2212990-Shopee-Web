package com.example.demo.services.user;

import com.example.demo.dtos.*;
import com.example.demo.models.users.User;

public interface IUserService {
    User createUser(UserRegisterDTO userRegisterDTO) throws Exception;
    String login(UserLoginDTO userLoginDTO) throws Exception;
    User getUserDetailFromPhoneNumberAndEmail(UserForgetPasswordDTO userForgetPasswordDTO) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User updateUserDetail(String userId, UserUpdateDTO userUpdateDTO) throws Exception;
    User resetPassword(String userId, UserResetPasswordDTO userResetPasswordDTO) throws Exception;
}
