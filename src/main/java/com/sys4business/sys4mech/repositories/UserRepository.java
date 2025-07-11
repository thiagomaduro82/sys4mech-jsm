package com.sys4business.sys4mech.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.sys4business.sys4mech.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuid(String uuid);

    Optional<UserDetails> findByEmail(String email);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

}
