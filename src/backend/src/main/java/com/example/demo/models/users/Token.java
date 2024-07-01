package com.example.demo.models.users;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@Document(collection = "tokens")
public class Token {
    @Id
    private String id;

    @DBRef
    User user;

    @Indexed(unique = true)
    private String token;

    private String refreshToken;

    private String tokenType;

    private LocalDateTime expirationDate;

    private LocalDateTime refreshExpirationDate;

    private boolean isMobile;

    private boolean revoked;

    private boolean expired;
}
