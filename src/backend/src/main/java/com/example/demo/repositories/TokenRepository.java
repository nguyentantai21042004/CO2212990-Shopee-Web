package com.example.demo.repositories;

import com.example.demo.models.users.Token;
import com.example.demo.models.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends MongoRepository<Token,String> {
    List<Token> findByUser(User user);
}
