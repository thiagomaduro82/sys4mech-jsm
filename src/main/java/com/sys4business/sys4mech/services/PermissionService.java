package com.sys4business.sys4mech.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.Permission;
import com.sys4business.sys4mech.repositories.PermissionRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission getByUuid(String uuid) {
        return permissionRepository.findByUuid(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Permission not found with UUID: " + uuid));
    }

    public Permission create(Permission permission) {
        permission.setId(null); // Ensure a new permission is created
        permission.setUuid(Sys4MechUtil.generateUuid());
        return permissionRepository.save(permission);
    }

    public Permission update(String uuid, Permission permission) {
        Permission existingPermission = getByUuid(uuid);
        existingPermission.setName(permission.getName());
        existingPermission.setDescription(permission.getDescription());
        return permissionRepository.save(existingPermission);
    }

    public void delete(String uuid) {
        Permission permission = getByUuid(uuid);
        permissionRepository.delete(permission);
    }

    public Page<Permission> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    public Page<Permission> searchByName(String name, Pageable pageable) {
        return permissionRepository.findByNameContainingIgnoreCase(name, pageable);
    }
    public Page<Permission> searchByDescription(String description, Pageable pageable) {
        return permissionRepository.findByDescriptionContainingIgnoreCase(description, pageable);
    }

    
    
}
