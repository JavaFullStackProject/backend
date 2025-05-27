package com.demo.service;

import com.demo.dto.LoginRequest;
import com.demo.dto.RegisterRequest;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

	//Handles database operations related to users.
    private final UserRepository userRepository;
    
    //Helps securely hash and verify user passwords.
    private final PasswordEncoder passwordEncoder;
    
    //Generates JWT tokens for authenticated users.
    private final JwtUtil jwtUtil;

    //Registering a New User
    public String register(RegisterRequest req) {
        Optional<User> userExists = userRepository.findByEmail(req.getEmail());
        
        //- Check if the user already exists
        if (userExists.isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        //- Create a new user
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();

        userRepository.save(user);

        //- Generate JWT token
        return jwtUtil.generateToken(user.getEmail());
    }

    //Logging in a User
    public String login(LoginRequest req) {
    	//- Find the user by email
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        //- Verify the password
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        //- Generate JWT token
        return jwtUtil.generateToken(user.getEmail());
    }
}

