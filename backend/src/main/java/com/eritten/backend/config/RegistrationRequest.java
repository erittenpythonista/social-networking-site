package com.eritten.backend.config;

public class RegistrationRequest {
    private String username;
    private String email;
    private String password;

    public RegistrationRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
