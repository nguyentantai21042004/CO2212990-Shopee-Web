package com.example.demo.services;

import com.example.demo.components.JwtTokenUtil;
import com.example.demo.dtos.UserLoginDTO;
import com.example.demo.dtos.UserRegisterDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.models.users.Role;
import com.example.demo.models.users.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(UserRegisterDTO userRegisterDTO) throws DataNotFoundException {
        String phoneNumber = userRegisterDTO.getPhoneNumber();
        String email = userRegisterDTO.getEmail();

        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }


        Role role = roleRepository.findByName(userRegisterDTO.getRoleName().toUpperCase())
                .orElseThrow(() -> new DataNotFoundException("Role is not found"));
        role.setName(userRegisterDTO.getRoleName().toUpperCase());

        User newUser = User.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .isActive(false)
                .role(role)
                .build();

        if(userRegisterDTO.getFacebookAccountId() == null && userRegisterDTO.getGoogleAccountId() == null){
            String password = userRegisterDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);

            newUser.setPassword(password);
            newUser.setPasswordHash(encodedPassword);
        }

        return userRepository.save(newUser);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(userLoginDTO.getPhoneNumber());

        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid Phone Number / Password");
        }
        User existingUser = optionalUser.get();

        if(userLoginDTO.getGoogleAccountId() == null
                && userLoginDTO.getFacebookAccountId() == null){
            if(!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())){
                throw new BadCredentialsException("wrong phone number or password");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);

        return jwtTokenUtil.generateToken(existingUser);
    }
}
