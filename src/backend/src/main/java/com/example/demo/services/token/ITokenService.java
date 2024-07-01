package com.example.demo.services.token;

import com.example.demo.models.users.Token;
import com.example.demo.models.users.User;

public interface ITokenService {
    public boolean isMobileDevice(String userAgent);
    public Token addToken(User user, String token, boolean isMobileDevice);

}
