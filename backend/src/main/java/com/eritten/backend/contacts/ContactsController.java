package com.eritten.backend.contacts;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eritten.backend.models.Contact;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ContactsController {
    private final ContactService contactService;

    @GetMapping("/contacts")
    public List<Contact> getContactsByEmail(@RequestParam String email) {
        return contactService.getContactsByEmail(email);
    }
    
}
