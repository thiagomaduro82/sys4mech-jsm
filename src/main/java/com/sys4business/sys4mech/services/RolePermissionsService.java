package com.sys4business.sys4mech.services;

import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.RolePermissions;
import com.sys4business.sys4mech.repositories.RolePermissionsRepository;

@Service
public class RolePermissionsService {

    private final RolePermissionsRepository rolePermissionsRepository;

    public RolePermissionsService(RolePermissionsRepository rolePermissionsRepository) {
        this.rolePermissionsRepository = rolePermissionsRepository;
    }

    public RolePermissions getById(Long id) {
        return rolePermissionsRepository
                .findById(id).orElseThrow(() -> new ObjectNotFoundException("Role Permission not found"));
    }

    public RolePermissions create(RolePermissions rolePermission) {
        rolePermission.setId(null); // Ensure a new ID is generated
        return rolePermissionsRepository.save(rolePermission);
    }

    public void delete(RolePermissions rolePermissions) {
        rolePermissionsRepository.delete(rolePermissions);
    }

}
