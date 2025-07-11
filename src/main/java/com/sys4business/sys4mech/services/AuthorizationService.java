package com.sys4business.sys4mech.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

  private final UserRepository userRepository;

  public AuthorizationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRepository.findByEmail(username).orElseThrow(() -> 
        new ObjectNotFoundException("User not found with email: " + username));
  }
    
}
