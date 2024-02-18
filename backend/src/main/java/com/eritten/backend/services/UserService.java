package com.eritten.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eritten.backend.models.User;
import com.eritten.backend.repositories.UserRepository;

import io.micrometer.common.lang.NonNull;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUserToDatabase(@NonNull User user) {
        userRepository.save(user);

    }

}
