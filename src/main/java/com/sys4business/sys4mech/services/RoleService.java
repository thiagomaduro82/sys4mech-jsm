package com.sys4business.sys4mech.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
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

    public Role findByUuid(String uuid) {
        return roleRepository.findByUuid(uuid)
                .orElseThrow(() -> new ObjectNotFoundException("Role not found with UUID: " + uuid));
    }

    public Page<Role> findAll(Predicate predicate, Pageable pageable) {
        return roleRepository.findAll(predicate, pageable);
    }

    public Role save(Role role) {
        role.setId(null); // Ensure a new role is created
        role.setUuid(Sys4MechUtil.generateUuid());
        return roleRepository.save(role);
    }

    public Role update(String uuid, Role role) {
        Role existingRole = findByUuid(uuid);
        existingRole.setName(role.getName());
        return roleRepository.save(existingRole);
    }

    public void deleteByUuid(String uuid) {
        Role role = findByUuid(uuid);
        roleRepository.delete(role);    
    }

}
