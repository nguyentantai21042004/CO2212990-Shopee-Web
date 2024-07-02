package com.example.demo.dtos.userDTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String fullName;

    private LocalDateTime dateOfBirth;

    private String facebookAccountId;

    private String googleAccountId;
}
