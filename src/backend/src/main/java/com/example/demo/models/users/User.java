package com.example.demo.models.users;


import com.example.demo.models.BaseEntity;
import com.example.demo.models.users.Address;
import com.example.demo.models.users.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class User extends BaseEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    @NotBlank(message = "userName is required")
    private String userName;

    @NotBlank(message = "password is required")
    private String password;

    private String fullName;

    @Indexed(unique = true)
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    private boolean isActive;

    private Date dateOfBirth;

    private int facebookAccountId;

    private int googleAccountId;

    private List<Address> addresses;

    private Role role;
}
