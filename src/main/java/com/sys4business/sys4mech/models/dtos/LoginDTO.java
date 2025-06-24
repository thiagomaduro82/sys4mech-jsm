package com.sys4business.sys4mech.models.dtos;

public record LoginDTO(String email, String password) {

    public LoginDTO {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
    }
    
}
