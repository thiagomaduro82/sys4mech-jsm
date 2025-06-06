package com.sys4business.sys4mech.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.Role;
import com.sys4business.sys4mech.repositories.RoleRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class RoleService {
    
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getByUuid(String uuid) {
        return roleRepository.findByUuid(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Role not found with UUID: " + uuid));
    }

    public Page<Role> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Page<Role> searchByName(String name, Pageable pageable) {
        return roleRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

    public Role create(Role role) {
        role.setId(null); // Ensure a new role is created
        role.setUuid(Sys4MechUtil.generateUuid());
        return roleRepository.save(role);
    }

    public Role update(String uuid, Role role) {
        Role existingRole = getByUuid(uuid);
        existingRole.setName(role.getName());
        return roleRepository.save(existingRole);
    }

    public void deleteByUuid(String uuid) {
        Role role = getByUuid(uuid);
        roleRepository.delete(role);    
    }

}
