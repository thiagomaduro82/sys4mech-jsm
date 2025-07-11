package com.sys4business.sys4mech.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.User;
import com.sys4business.sys4mech.models.dtos.LoginDTO;
import com.sys4business.sys4mech.models.dtos.LoginResponseDTO;
import com.sys4business.sys4mech.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            log.info("Attempting to authenticate user: {}", loginDTO.email());
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            List<String> permissions = auth.getAuthorities().stream().map(authority -> authority.getAuthority())
                    .toList();
            return ResponseEntity.ok(new LoginResponseDTO(token, permissions));
        } catch (Exception e) {
            log.error("Login failed for user: {}", loginDTO.email(), e);
            return ResponseEntity.status(401).build();
        }

    }

    @GetMapping("/me/permissions")
    public ResponseEntity<List<String>> getUserPermissions(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<String> permissions = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority()).toList();
        return ResponseEntity.ok(permissions);
    }

}
