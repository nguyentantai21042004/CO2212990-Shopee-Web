package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResetPasswordDTO {
    @NotBlank(message = "New password is required")
    private String newPassword;

    @NotBlank(message = "Retype new password is required")
    private String retypeNewPassword;

    @NotBlank(message = "Password is required")
    private String oldPassword;
}
