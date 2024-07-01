package com.example.demo.models.users;

import com.example.demo.models.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "addresses")
public class Address extends BaseEntity {
    @Id
    private String id;

    private String street;

    private String ward;

    private String district;

    @NotBlank(message = "City is required")
    private String city;
}
