package com.sys4business.sys4mech.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByUuid(String uuid);

    Page<Permission> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    Page<Permission> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    
}
