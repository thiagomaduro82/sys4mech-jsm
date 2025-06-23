package com.sys4business.sys4mech.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.User;
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

  public List<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable).toList();
  }

  public User create(User user) {
    user.setId(null);
    user.setUuid(Sys4MechUtil.generateUuid());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User update(User user) {
    return userRepository.save(user);
  }

  public void delete(String uuid) {
    User user = getByUuid(uuid);
    userRepository.delete(user);
  }
    
}
