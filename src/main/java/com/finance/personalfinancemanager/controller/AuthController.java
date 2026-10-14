package com.finance.personalfinancemanager.controller;

import com.finance.personalfinancemanager.model.User;
import com.finance.personalfinancemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // allows frontend requests
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        System.out.println("Login attempt for email: " + loginRequest.getEmail());
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            System.out.println("❌ User not found");
            return ResponseEntity.status(404).body("User not found");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            System.out.println("❌ Invalid password for: " + user.getEmail());
            return ResponseEntity.status(401).body("Invalid password");
        }

        System.out.println("✅ Login successful for: " + user.getEmail());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
    if (userRepository.findByEmail(newUser.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // Basic validation
        if (newUser.getName() == null || newUser.getEmail() == null || newUser.getPassword() == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }

        User savedUser = userRepository.save(newUser);
        return ResponseEntity.ok(savedUser);
    }

}
