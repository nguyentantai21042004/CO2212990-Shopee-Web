package com.example.demo.models.users;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    private String id;

    @NotNull(message = "name is required")
    @Indexed(unique = true)
    private RoleName name;

    private String description;

    public enum RoleName {
        USER,
        ADMIN,
        MANAGER
    }
}
