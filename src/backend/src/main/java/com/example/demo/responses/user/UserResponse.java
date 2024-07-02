package com.example.demo.responses.user;

import com.example.demo.models.users.Address;
import com.example.demo.models.users.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;

    private String email;

    private String fullName;

    private String password;

    private String originalPassword;

    private String phoneNumber;

    private LocalDateTime dateOfBirth;

    private boolean isActive;

    private List<Address> addresses;

    private String facebookAccountId;

    private String googleAccountId;

    private String roleName;

    public static UserResponse fromUser(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .originalPassword(user.getOriginalPassword())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .isActive(user.isActive())
                .addresses(user.getAddresses())
                .facebookAccountId(user.getFacebookAccountId())
                .googleAccountId(user.getGoogleAccountId())
                .roleName(user.getRole().getName())
                .build();
    }
}
