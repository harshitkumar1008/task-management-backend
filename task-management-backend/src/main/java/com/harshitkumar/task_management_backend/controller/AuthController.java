package com.harshitkumar.task_management_backend.controller;


import com.harshitkumar.task_management_backend.entity.User;
import com.harshitkumar.task_management_backend.repository.UserRepository;
import com.harshitkumar.task_management_backend.utils.JWTUtil;
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
            userRepository.save(user);
            return ResponseEntity.ok("User Registered Successfully");
        }
    }

    @PostMapping({"/login"})
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> user = this.userRepository.findByUsername(loginRequest.getUsername());
        if (user.isPresent() && ((User)user.get()).getPassword().equals(loginRequest.getPassword())) {
            String token = this.jwtUtil.generateToken(loginRequest.getPassword());
            return ResponseEntity.ok().body("Bearer " + token);
        } else {
            return ResponseEntity.badRequest().body("Invalid Username or Password");
        }
    }
}
