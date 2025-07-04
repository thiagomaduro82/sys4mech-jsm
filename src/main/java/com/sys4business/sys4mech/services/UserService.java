package com.sys4business.sys4mech.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.User;
import com.sys4business.sys4mech.models.dtos.ChangePassword;
import com.sys4business.sys4mech.repositories.UserRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User getByUuid(String uuid) {
    return userRepository.findByUuid(uuid)
        .orElseThrow(() -> new ObjectNotFoundException("User not found - UUID: " + uuid));
  }

  public Page<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public Page<User> searchByName(String name, Pageable pageable) {
    if (name == null || name.isEmpty()) {
      return userRepository.findAll(pageable);
    }
    return userRepository.findByNameContainingIgnoreCase(name, pageable);
  }

  public Page<User> searchByEmail(String email, Pageable pageable) {
    if (email == null || email.isEmpty()) {
      return userRepository.findAll(pageable);
    }
    return userRepository.findByEmailContainingIgnoreCase(email, pageable);
  }

  public User create(User user) {
    user.setId(null);
    user.setUuid(Sys4MechUtil.generateUuid());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User update(String uuid, User user) {
    User existingUser = getByUuid(uuid);
    existingUser.setName(user.getName());
    existingUser.setEmail(user.getEmail());
    existingUser.setRole(user.getRole());
    return userRepository.save(existingUser);
  }

  public void delete(String uuid) {
    User user = getByUuid(uuid);
    userRepository.delete(user);
  }

  public User changePassword(String uuid, ChangePassword changePassword) {
    User user = getByUuid(uuid);
    user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
    return userRepository.save(user);
  }
    
}
