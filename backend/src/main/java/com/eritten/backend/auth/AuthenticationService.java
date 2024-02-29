package com.eritten.backend.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eritten.backend.config.JwtService;
import com.eritten.backend.models.User;
import com.eritten.backend.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthenticationService {
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                var user = User.builder()
                                .fullName(request.getFullname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .build();
                var savedUser = repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                                                request.getPassword()));
                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        public ChangeEmailResponse changeEmail(ChangeEmailRequest request) {
                // Retrieve the current user by email
                Optional<User> currentUserOptional = repository.findByEmail(request.getEmail());

                // Check if the user exists
                if (!currentUserOptional.isPresent()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
                }

                // Get the user from the Optional
                User currentUser = currentUserOptional.get();

                // Update the user's email
                currentUser.setEmail(request.getNewEmail());

                // Save the updated user
                repository.save(currentUser);

                // Create and return the response
                return new ChangeEmailResponse("Email updated successfully");
        }

        public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
                User currentUser = repository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                "User does not exist"));

                if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid current password");
                }

                String newPasswordEncoded = passwordEncoder.encode(request.getNewPassword());
                currentUser.setPassword(newPasswordEncoded);
                repository.save(currentUser);

                return new ChangePasswordResponse("Password changed successfully");
        }
}
