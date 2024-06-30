package com.example.demo.models.users;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@Document(collection = "roles")
public class Role {
    @Id
    private String id;

    @NotNull(message = "name is required")
    @Indexed(unique = true)
    private String name;

    private String description;
}
