package com.example.demo.models.users;

import com.example.demo.models.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User extends BaseEntity implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Indexed(unique = true)
    @NotBlank(message = "FullName is required")
    private String fullName;

    @NotBlank(message = "Password is required")
    private String password;            // Hash Password

    @NotBlank(message = "Password original is required")
    private String originalPassword;    // Original Password

    @Indexed(unique = true)
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private LocalDateTime dateOfBirth;

    private boolean isActive;

    private List<Address> addresses;


    private String facebookAccountId;

    private String googleAccountId;

    @DBRef
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + getRole().getName().toUpperCase()));

        return authorityList;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
