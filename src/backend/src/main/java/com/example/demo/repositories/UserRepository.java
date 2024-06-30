package com.example.demo.repositories;

import com.example.demo.models.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByPhoneNumber(String phoneNumber);

    /* Optional is used to separate 2 separate cases: null and yes */
    Optional<User> findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);
}