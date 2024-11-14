package com.campus.connect.participant.backend.controller;

import com.campus.connect.participant.backend.model.User;
import com.campus.connect.participant.backend.repository.UserRepository;
import com.campus.connect.participant.backend.payload.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User userDetails) {
        int rowsAffected = userRepository.updateUser(id, userDetails);
        if (rowsAffected > 0) {
            userDetails.setId(id);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        int rowsAffected = userRepository.deleteUser(id);
        if (rowsAffected > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> GetEmail(@RequestParam String email){
        System.err.print("in req");
        Optional<User> response = userRepository.findByEmail(email);

        if(response.isPresent())
        {
            return ResponseEntity.ok(response.get());
        }
        else
        { 
            return ResponseEntity.notFound().build();
        }

    }

    
    @PostMapping("/signup")
    public ResponseEntity<?> signIn(@RequestBody SignupRequest signupRequest) {
        
        Optional<User> response = userRepository.findByEmail(signupRequest.getEmail());

        if(response.isPresent())
        {
           return ResponseEntity.badRequest().body("Error: Email already exists.");
        }
        else
        { 
            User user = new User();
            user.setEmail(signupRequest.getEmail());
            String hashedPassword = new BCryptPasswordEncoder().encode(signupRequest.getPassword());
            user.setPassword(hashedPassword);
            user.setRole("participant");
            userRepository.createUser(user);
            // return ResponseEntity.ok().body(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(
            Map.of(
                "message", "User successfully registered.",
                "user", user
            )
        );
        }

    }
    


}