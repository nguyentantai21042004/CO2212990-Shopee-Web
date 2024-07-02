package com.example.demo.responses.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String message;

    private String token;

    private String refreshToken;

    private String tokenType = "Bearer";

    private String username;

    private String userId;

    private List<String> roles;
}
