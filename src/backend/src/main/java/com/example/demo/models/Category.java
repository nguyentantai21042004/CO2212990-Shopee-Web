package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "categories")
public class Category extends BaseEntity{
    public static final int MAXIMUM_IMAGES_PER_CATEGORY = 1;

    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Category's name is required")
    private String name;

    private String description;

    private String parentId;

    private String status;

    private String imageUrl;
}
