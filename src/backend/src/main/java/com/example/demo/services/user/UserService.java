package com.example.demo.services.user;

import com.example.demo.components.JwtTokenUtil;
import com.example.demo.dtos.UserLoginDTO;
import com.example.demo.dtos.UserRegisterDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.exceptions.InvalidParamException;
import com.example.demo.exceptions.PermissionDenyException;
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
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

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
                .phoneNumber(phoneNumber)
                .email(email)
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
}
