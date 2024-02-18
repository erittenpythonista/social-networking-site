package com.eritten.backend.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eritten.backend.models.User;
import com.eritten.backend.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        Optional<User> tempUser = userRepository.findByEmail(user.getUsername());
        if (tempUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists.");
        }
        userRepository.save(user);
        return tempUser.get();
    }
}
