package com.example.demo.responses;

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

    private List<String> roles;
}
