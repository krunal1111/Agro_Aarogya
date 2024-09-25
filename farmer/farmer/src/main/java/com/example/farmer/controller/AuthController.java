package com.example.farmer.controller;

import com.example.farmer.model.User;
import com.example.farmer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://agro-arogya2.s3-website.ap-south-1.amazonaws.com")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userService.registerUser(user);
        
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User loggedInUser = userService.validateUser(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            return loggedInUser; // Return the entire user object
        } else {
            throw new RuntimeException("Invalid username or password!"); // Or handle this case differently
        }
    }
}
