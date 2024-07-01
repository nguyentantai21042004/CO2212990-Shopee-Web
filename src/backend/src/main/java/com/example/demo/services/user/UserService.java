package com.example.demo.services.user;

import com.example.demo.components.JwtTokenUtil;
import com.example.demo.dtos.*;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.exceptions.ExpiredTokenException;
import com.example.demo.exceptions.PermissionDenyException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.models.users.Role;
import com.example.demo.models.users.Token;
import com.example.demo.models.users.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.TokenRepository;
import com.example.demo.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public User createUser(UserRegisterDTO userRegisterDTO) throws Exception {
        String phoneNumber = userRegisterDTO.getPhoneNumber();
        String email = userRegisterDTO.getEmail();

        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }


        Role role = roleRepository.findByName(userRegisterDTO.getRoleName().toUpperCase())
                .orElseThrow(() -> new DataNotFoundException("Role is not found"));
        role.setName(userRegisterDTO.getRoleName().toUpperCase());

        if(role.getName().equals("ADMIN")){
            throw new PermissionDenyException("You can not create an admin account here. Please contact to hotline");
        }

        User newUser = User.builder()
                .email(email)
                .phoneNumber(phoneNumber)
                .isActive(true)
                .role(role)
                .build();

        if(userRegisterDTO.getFacebookAccountId() == null && userRegisterDTO.getGoogleAccountId() == null){
            String password = userRegisterDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);

            newUser.setOriginalPassword(password);
            newUser.setPassword(encodedPassword);
        }

        return userRepository.save(newUser);
    }


    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(userLoginDTO.getPhoneNumber());
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("Not found");
        }
        User existingUser = optionalUser.get();

        if(existingUser.getFacebookAccountId() == null && existingUser.getGoogleAccountId() == null){
            if (!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())) {
                throw new BadCredentialsException("WRONG_PHONE_PASSWORD");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword(), existingUser.getAuthorities());
        authenticationManager.authenticate(authenticationToken);

        return jwtTokenUtil.generateToken(existingUser);
    }

    @Transactional
    @Override
    public User getUserDetailFromPhoneNumberAndEmail(UserForgetPasswordDTO userForgetPasswordDTO) throws Exception {
        User existingUser = userRepository.findByPhoneNumber(userForgetPasswordDTO.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("Invalid user"));
        if(!existingUser.getEmail().equals(userForgetPasswordDTO.getEmail()))
            throw new PermissionDenyException("Something wrong");

        return existingUser;
//
//        String newPassword = UUID.randomUUID().toString().substring(0, 5);
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        existingUser.setOriginalPassword(newPassword);
//        existingUser.setPassword(encodedPassword);
//
//        return userRepository.save(existingUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)){
            throw new ExpiredTokenException("Token is expired");
        }

        String subject = jwtTokenUtil.extractClaim(token, Claims::getSubject); // Phone Number
        Optional<User> user;
        user = userRepository.findByPhoneNumber(subject);

        return user.orElseThrow(() -> new Exception("User not found"));
    }

    @Transactional
    @Override
    public User updateUserDetail(String userId, UserUpdateDTO userUpdateDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if(userUpdateDTO.getFullName() != null)
            existingUser.setFullName(userUpdateDTO.getFullName());

        if(userUpdateDTO.getDateOfBirth() != null)
            existingUser.setDateOfBirth(userUpdateDTO.getDateOfBirth());

        if (userUpdateDTO.getFacebookAccountId() != null) {
            existingUser.setFacebookAccountId(userUpdateDTO.getFacebookAccountId());
        }
        if (userUpdateDTO.getGoogleAccountId() != null) {
            existingUser.setGoogleAccountId(userUpdateDTO.getGoogleAccountId());
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    @Override
    public User resetPassword(String userId, UserResetPasswordDTO userResetPasswordDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if(!userResetPasswordDTO.getNewPassword().equals(userResetPasswordDTO.getRetypeNewPassword())){
            throw new DataNotFoundException("RETYPE NEW PASSWORD IS NOT THE SAME");
        }

        if(!existingUser.getOriginalPassword().equals(userResetPasswordDTO.getOldPassword())){
            throw new DataNotFoundException("WRONG PASSWORD");
        }

        String password = userResetPasswordDTO.getNewPassword();
        String encodedPassword = passwordEncoder.encode(password);
        existingUser.setOriginalPassword(password);
        existingUser.setPassword(encodedPassword);


        /* Reset password => clear token */
        List<Token> tokens = tokenRepository.findByUser(existingUser);
        for (Token token : tokens) {
            tokenRepository.delete(token);
        }
        return userRepository.save(existingUser);
    }
}
