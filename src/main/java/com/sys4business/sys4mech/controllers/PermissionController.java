package com.sys4business.sys4mech.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.Permission;
import com.sys4business.sys4mech.models.dtos.PermissionDTO;
import com.sys4business.sys4mech.services.PermissionService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final Logger log = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<Permission> getPermissionByUuid(@PathVariable String uuid) {
        log.info("Fetching permission with UUID: {}", uuid);
        Permission permission = permissionService.getByUuid(uuid);
        return ResponseEntity.ok(permission);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<Page<Permission>> getAllPermissions(
            @RequestParam Optional<String> field,
            @RequestParam Optional<String> value,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info("Fetching permissions with parameters: field={}, value={}, pageNumber={}, pageSize={}, order={}",
                field.orElse(""), value.orElse(""), pageNumber.orElse(Constant.INITIAL_PAGE), pageSize.orElse(Constant.PAGE_SIZE),
                order.orElse("asc"));

        Sort sortRequest;
        if (order.isPresent()) {
            if (order.get().equalsIgnoreCase("asc")) {
                sortRequest = Sort.by(field.orElse("name")).ascending();
            } else {
                sortRequest = Sort.by(field.orElse("name")).descending();
            }
        } else {
            sortRequest = Sort.by(field.orElse("name")).ascending();
        }

        // Set up pageable variable
        Pageable pageable;
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            pageable = PageRequest.of(pageNumber.orElse(Constant.INITIAL_PAGE), pageSize.get(), sortRequest);
        } else {
            pageable = PageRequest.of(Constant.INITIAL_PAGE, Constant.PAGE_SIZE, sortRequest);
        }

        if (field.isPresent()) {
            if (field.get().equalsIgnoreCase("name") && value.isPresent()) {
                return ResponseEntity.ok(permissionService.searchByName(value.get(), pageable));
            }
            return ResponseEntity.ok(permissionService.searchByDescription(value.get(), pageable));
        }
        return ResponseEntity.ok(permissionService.findAll(pageable));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public ResponseEntity<List<Permission>> getAllPermissionsList() {
        log.info("Fetching all permissions as a list");
        List<Permission> permissions = permissionService.findAllList();
        return ResponseEntity.ok(permissions);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_WRITE')")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        log.info("Creating permission with details: {}", permissionDTO);
        Permission createdPermission = permissionService.create(permissionDTO.toPermission());
        URI location = URI.create("/api/permissions/" + createdPermission.getUuid());
        return ResponseEntity.created(location).body(createdPermission);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('PERMISSION_WRITE')")
    public ResponseEntity<Permission> updatePermission(@PathVariable String uuid, @Valid @RequestBody PermissionDTO permissionDTO) {
        log.info("Updating permission with UUID: {} and details: {}", uuid, permissionDTO);
        Permission updatedPermission = permissionService.update(uuid, permissionDTO.toPermission());
        return ResponseEntity.ok(updatedPermission);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public ResponseEntity<Void> deletePermission(@PathVariable String uuid) {
        log.info("Deleting permission with UUID: {}", uuid);
        permissionService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
    
}
