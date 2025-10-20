package com.willianac.video_analyzer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.rpc.context.AttributeContext.Response;
import com.willianac.video_analyzer.model.User;
import com.willianac.video_analyzer.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String testApi() {
        return "User endpoint is working!";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        try {
            Iterable<User> users = userRepository.findAll();
            for (User existingUser : users) {
                if (existingUser.getName().equals(user.getName())) {
                    return ResponseEntity.ok(existingUser);
                }
            }
            User savedUser = userRepository.save(user);
            
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating user: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/summaries")
    public ResponseEntity<?> getUserSummaries(@RequestParam Long userId) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }
            return ResponseEntity.ok(user.getSummaryRequests());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving summaries: " + e.getMessage());
        }
    }
}
