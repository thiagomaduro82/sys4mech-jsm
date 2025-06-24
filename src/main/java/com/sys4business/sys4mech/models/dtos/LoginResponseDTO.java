package com.sys4business.sys4mech.models.dtos;

public record LoginResponseDTO(String token) {

    public LoginResponseDTO {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null or blank");
        }
    }
    
}
