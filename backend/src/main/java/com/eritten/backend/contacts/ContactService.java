package com.eritten.backend.contacts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eritten.backend.models.Contact;
import com.eritten.backend.models.User;
import com.eritten.backend.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContactService {
    private final UserRepository userRepository;

    public List<Contact> getContactsByEmail(String email) {
        // Find the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Return the contacts associated with the user
        return user.getContacts();
    }
}
