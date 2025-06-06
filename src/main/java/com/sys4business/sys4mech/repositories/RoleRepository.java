package com.sys4business.sys4mech.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByUuid(String uuid);

    Page<Role> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    
}
