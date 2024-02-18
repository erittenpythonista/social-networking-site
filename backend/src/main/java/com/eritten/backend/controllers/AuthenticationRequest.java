package com.eritten.backend.controllers;

public class AuthenticationRequest {
    private String email;
    String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
