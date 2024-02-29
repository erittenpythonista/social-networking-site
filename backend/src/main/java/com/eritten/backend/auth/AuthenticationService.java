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
import com.eritten.backend.services.MailService;
import com.eritten.backend.services.VerificationCodeGeneratorService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthenticationService {
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final VerificationCodeGeneratorService verificationCodeGeneratorService;
        private final MailService mailService;

        public AuthenticationResponse register(RegisterRequest request) {
                final String verificationCode = verificationCodeGeneratorService.generateRandomCode(6);
                final String fullname = request.getFullname();
                final String emailMessage = "Subject: Email Verification for Signup on Our Social Networking Platform\n\n"
                                +
                                "Dear " + fullname + ",\n\n" +
                                "Thank you for choosing to sign up for our social networking platform. We are excited to have you join our community!\n\n"
                                +
                                "To ensure the security of your account and to complete the signup process, we require email verification. Please use the following code:\n\n"
                                +
                                verificationCode + "\n\n" +
                                "Once you have received the verification code, please enter it in the appropriate field on our website to continue with the signup process.\n\n"
                                +
                                "If you have any questions or encounter any issues during the signup process, please do not hesitate to contact our support team at eritten.gyau@amalitech.com for assistance.\n\n"
                                +
                                "We appreciate your interest in our platform and look forward to seeing you online soon!\n\n"
                                +
                                "Best regards,\n" +
                                "Amalitech";

                var user = User.builder()
                                .fullName(request.getFullname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .verificationCode(verificationCode)
                                .build();
                var savedUser = repository.save(user);
                mailService.sendEmail(request.getEmail(), "Social networking site email verification", emailMessage);

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
