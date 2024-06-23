package com.example.demo.controllers;

import com.example.demo.DTObjects.UserRegisterDTO;
import com.example.demo.services.IUserService;
import com.example.demo.services.tes.EmailService;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;



@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final EmailService emailService;

    @GetMapping("")
    public ResponseEntity<String> Test (@RequestBody UserRegisterDTO userDTO) {
        try {
        // Validate userDto (you can use Bean Validation annotations like @NotBlank, etc.)
        // Save user to database (UserService should handle this logic)
//        User savedUser = userService.saveUser(userDto);

        // Send confirmation email
        String to = userDTO.getEmail();
        String subject = "Welcome to Our Application";
        String text = "Dear " + userDTO.getPhoneNumber() + ",\n\n"
                + "Thank you for registering with us.";

        emailService.sendEmail(to, subject, text);

        return ResponseEntity.ok("User registered successfully. Confirmation email sent.");
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email.");
        }
    }


}
