package com.example.demo.controllers;

import com.example.demo.dtos.userDTOS.UserLoginDTO;
import com.example.demo.dtos.userDTOS.UserRegisterDTO;
import com.example.demo.dtos.userDTOS.UserResetPasswordDTO;
import com.example.demo.dtos.userDTOS.UserUpdateDTO;
import com.example.demo.models.users.Token;
import com.example.demo.models.users.User;
import com.example.demo.responses.user.LoginResponse;
import com.example.demo.responses.ResponseObject;
import com.example.demo.responses.user.UserListResponse;
import com.example.demo.responses.user.UserResponse;
import com.example.demo.services.token.ITokenService;
import com.example.demo.services.user.IUserService;
import com.example.demo.utils.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final ITokenService tokenService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> getAllUser(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("createdAt").ascending()
        );

        Page<UserResponse> userPage = userService.findAllUser(keyword, pageRequest)
                .map(UserResponse::fromUser);

        int totalPages = userPage.getTotalPages();
        List<UserResponse> userResponses = userPage.getContent();

        UserListResponse userListResponse = UserListResponse.builder()
                .users(userResponses)
                .totalPage(totalPages)
                .build();

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .data(userListResponse)
                        .message("Get user list successfully")
                        .status(HttpStatus.OK)
                        .build());
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO,
            BindingResult result
    ) throws Exception {
        if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();

                return ResponseEntity.badRequest().body(
                        ResponseObject.builder()
                                .data(null)
                                .message(errorMessages.toString())
                                .status(HttpStatus.BAD_REQUEST)
                                .build());
        }

        if(userRegisterDTO.getEmail() == null || userRegisterDTO.getEmail().trim().isBlank()){
            if(userRegisterDTO.getPhoneNumber() == null || userRegisterDTO.getPhoneNumber().isBlank()){
                /* Don't have Email and Phone number */
                return ResponseEntity.badRequest().body(
                        ResponseObject.builder()
                                .data(null)
                                .message("At least email or phone number is required")
                                .status(HttpStatus.BAD_REQUEST)
                                .build());
            } else{
                /* Just have a Phone number */
                if(!ValidationUtils.isValidPhoneNumber(userRegisterDTO.getPhoneNumber())){
                    throw new Exception("Invalid Phone number");
                }
            }
        } else {
            /* Have Email and maybe have Phone number */
            if(!ValidationUtils.isValidEmail(userRegisterDTO.getEmail())){
                throw new Exception("Invalid Email format");
            }
        }

//        if(!userRegisterDTO.getPassword().equals(userRegisterDTO.getRetypePassword())){
//            return ResponseEntity.badRequest().body(
//                    ResponseObject.builder()
//                            .data(null)
//                            .message("PASSWORD_NOT_MATCH")
//                            .status(HttpStatus.BAD_REQUEST)
//                            .build());
//        }

        User newUser = userService.createUser(userRegisterDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .data(newUser)
                        .message("Create new user successfully")
                        .status(HttpStatus.OK)
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            HttpServletRequest request
    ) throws Exception{
        String token = userService.login(userLoginDTO);                                                     // Generate a token ==> Login
        String userAgent = request.getHeader("User-Agent");                                              // To check isMobile or not ==> Priority to Delete
        User userDetail = userService.getUserDetailsFromToken(token);                                       // Get Existing User
        Token jwtToken = tokenService.addToken(userDetail, token, tokenService.isMobileDevice(userAgent));  // ADD new Token to Database

        LoginResponse loginResponse = LoginResponse.builder()
                .message("Login Successfully")
                .token(jwtToken.getToken())
                .refreshToken(jwtToken.getRefreshToken())
                .tokenType("Bearer")
                .username(userDetail.getUsername())
                .userId(userDetail.getId())
                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();

        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message("Login Successfully")
                        .data(loginResponse)
                        .status(HttpStatus.OK)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_SELLER')")
    @PostMapping("/details")
    public ResponseEntity<ResponseObject> getUserDetail(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7); // Remove "Bearer "
        User user = userService.getUserDetailsFromToken(extractedToken);

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Get user's detail successfully")
                .data(user)
                .status(HttpStatus.OK)
                .build());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_SELLER')")
    @PutMapping("/details/{userId}")
    public ResponseEntity<ResponseObject> updateUserDetail(
            @PathVariable String userId,
            @RequestBody UserUpdateDTO userUpdateDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        try {
            String extractedToken = authorizationHeader.substring(7); // Remove "Bearer "
            User user = userService.getUserDetailsFromToken(extractedToken);

            if(!Objects.equals(user.getId(), userId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            User updateUser = userService.updateUserDetail(userId, userUpdateDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Update user's detail successfully")
                    .data(updateUser)
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message(e.getMessage())
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_SELLER')")
    @PutMapping("/details/resetPassword/{userId}")
    public ResponseEntity<ResponseObject> changePassword(
            @PathVariable String userId,
            @RequestBody UserResetPasswordDTO userResetPasswordDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        try {
            String extractedToken = authorizationHeader.substring(7); // Remove "Bearer "
            User user = userService.getUserDetailsFromToken(extractedToken);

            if(!Objects.equals(user.getId(), userId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            User updateUser = userService.resetPassword(userId, userResetPasswordDTO);
            return ResponseEntity.ok().body(ResponseObject.builder()
                    .message("Update user's detail PASSWORD successfully")
                    .data(updateUser)
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message(e.getMessage())
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_SELLER')")
    @DeleteMapping("/details/{userId}")
    public ResponseEntity<ResponseObject> deleteUserDetail(
            @PathVariable String userId,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws Exception {
        String extractedToken = authorizationHeader.substring(7); // Remove "Bearer "
        User user = userService.getUserDetailsFromToken(extractedToken);

        if(!Objects.equals(user.getId(), userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.delete(userId);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Delete All User successfully")
                .data(null)
                .status(HttpStatus.OK)
                .build());
    }
}
