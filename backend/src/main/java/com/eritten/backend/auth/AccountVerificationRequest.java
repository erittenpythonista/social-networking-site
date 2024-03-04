package com.eritten.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountVerificationRequest {
    private final String email;
    private final String code;

}
