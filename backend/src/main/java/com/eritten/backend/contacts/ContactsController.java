package com.eritten.backend.contacts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eritten.backend.models.Contact;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
public class ContactsController {

    private final ContactService contactService;

    @GetMapping
    public List<Contact> getContactsByEmail(@RequestParam String email) {
        return contactService.getContactsByEmail(email);
    }

    @PostMapping("/add")
    public ResponseEntity<AddContactResponse> addContact(@RequestBody AddContactRequest request) {
        AddContactResponse response = contactService.addContact(request);
        return ResponseEntity.ok(response);
    }
}
