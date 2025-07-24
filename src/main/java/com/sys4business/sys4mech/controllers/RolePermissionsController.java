package com.sys4business.sys4mech.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.RolePermissions;
import com.sys4business.sys4mech.services.RolePermissionsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionsController {

    private static final Logger log = LoggerFactory.getLogger(RolePermissionsController.class);

    private final RolePermissionsService rolePermissionsService;

    public RolePermissionsController(RolePermissionsService rolePermissionsService) {
        this.rolePermissionsService = rolePermissionsService;
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PERMISSIONS_READ')")
    public ResponseEntity<RolePermissions> getRolePermission(@RequestParam Long roleId, @RequestParam Long permissionId) {
        RolePermissions rolePermissions = rolePermissionsService.getByRoleIdAndPermissionId(roleId, permissionId);
        return ResponseEntity.ok().body(rolePermissions);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_PERMISSIONS_WRITE')")
    public ResponseEntity<RolePermissions> createRolePermission(@Valid @RequestBody RolePermissions rolePermissions) {
        log.info("Creating role with details: {}", rolePermissions);
        return ResponseEntity.ok().body(rolePermissionsService.create(rolePermissions));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PERMISSIONS_DELETE')")
    public ResponseEntity<Void> deleteRolePermission(@PathVariable Long id) {
        RolePermissions rolePermissions = rolePermissionsService.getById(id);
        log.info("Deleting permission: {}", rolePermissions);
        rolePermissionsService.delete(rolePermissions);
        return ResponseEntity.noContent().build();
    }

}
