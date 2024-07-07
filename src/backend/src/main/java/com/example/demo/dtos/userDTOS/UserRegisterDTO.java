package com.example.demo.dtos.userDTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

//    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

//    @NotBlank(message = "RetypePassword is required")
    private String retypePassword;

    @Builder.Default
    private String roleName = "USER";

    private String facebookAccountId;

    private String googleAccountId;
}
