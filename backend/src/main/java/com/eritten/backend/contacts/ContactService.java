package com.eritten.backend.contacts;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import com.eritten.backend.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Service
public class ContactService {
    private final UserRepository userRepository;

    public List<Contact> getContacts() {

    }

}
