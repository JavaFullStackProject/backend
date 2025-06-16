// src/main/java/com/demo/controller/UserController.java
package com.demo.controller;

import com.demo.dto.UserProfileResponse;
import com.demo.model.User;
import com.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal UserDetails principal) {
        String email = principal.getUsername(); // From JWT

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponse response = new UserProfileResponse(
                user.getName(),
                user.getEmail(),
                //user.getCity() != null ? user.getCity() : "Not Set",
                true // Replace with actual logic if needed
        );

        return ResponseEntity.ok(response);
    }
    
}
