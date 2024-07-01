package com.example.demo.controllers;

import com.example.demo.dtos.UserLoginDTO;
import com.example.demo.dtos.UserRegisterDTO;
import com.example.demo.models.users.User;
import com.example.demo.responses.ResponseObject;
import com.example.demo.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> createUser(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO,
            BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(
                        ResponseObject.builder()
                                .data(null)
                                .message(String.valueOf(errorMessages))
                                .status(HttpStatus.BAD_REQUEST)
                                .build());
            }
            User newUser = userService.createUser(userRegisterDTO);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .data(newUser)
                            .message("Create new user successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .data(null)
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String token = userService.login(userLoginDTO);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .data(token)
                            .message("Login successfully")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .data(null)
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .build());
        }
    }
}
