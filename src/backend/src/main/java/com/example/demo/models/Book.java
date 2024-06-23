package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor // to_String
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "book")
public class Book {
    @Id
    private String id;

    private long bookId;

    private String isbnNumber;

    private String category;

    private String bookName;
}
