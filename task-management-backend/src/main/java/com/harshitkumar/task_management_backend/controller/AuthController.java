package com.harshitkumar.task_management_backend.controller;


import com.harshitkumar.task_management_backend.dto.RefreshTokenRequest;
import com.harshitkumar.task_management_backend.dto.TokenResponse;
import com.harshitkumar.task_management_backend.entity.User;
import com.harshitkumar.task_management_backend.repository.UserRepository;
import com.harshitkumar.task_management_backend.utils.JWTUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        } else if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        } else {
            String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getRole().name());
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
            return ResponseEntity.ok("User Registered Successfully: Access Token - " + accessToken);
        }
    }

    @PostMapping({"/login"})
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isPresent() && (user.get()).getPassword().equals(loginRequest.getPassword())) {
            String accessToken = jwtUtil.generateToken(loginRequest.getUsername(), loginRequest.getRole().name());
            String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername(), loginRequest.getRole().name());
            user.get().setRefreshToken(refreshToken);
            return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken).toString());
        } else {
            return ResponseEntity.badRequest().body("Invalid Username or Password");
        }
    }


    @PostMapping({"/refresh"})
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            String username = jwtUtil.extractUsernameFromRefreshClaim(refreshToken);
            String role = jwtUtil.extractRoleFromRefreshClaim(refreshToken);
            String newAccessToken = jwtUtil.generateToken(username, role);
            return ResponseEntity.ok(new TokenResponse(newAccessToken, refreshToken).toString());
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}
