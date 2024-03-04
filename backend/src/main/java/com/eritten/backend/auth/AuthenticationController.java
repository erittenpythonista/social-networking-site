package com.eritten.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(service.changePassword(request));
    }

    @PostMapping("/change-email")
    public ResponseEntity<ChangeEmailResponse> changeEmail(@RequestBody ChangeEmailRequest request) {
        return ResponseEntity.ok(service.changeEmail(request));
    }

    @PostMapping("/change-fullname")
    public ResponseEntity<ChangeFullnameResponse> changeEmail(@RequestBody ChangeFullnameRequest request) {
        return ResponseEntity.ok(service.changeFullname(request));
    }

    @PostMapping("/upload-profile-image")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("email") String email,
                                                     @RequestParam("file") MultipartFile file) {
        String imageUrl = service.uploadProfileImage(email, file);
        return ResponseEntity.ok(imageUrl);
    }
}
