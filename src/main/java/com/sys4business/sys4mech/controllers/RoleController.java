package com.sys4business.sys4mech.controllers;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.Role;
import com.sys4business.sys4mech.models.dtos.RoleDTO;
import com.sys4business.sys4mech.services.RoleService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Role> getRoleByUuid(@PathVariable String uuid) {
        log.info("Fetching role with UUID: {}", uuid);
        Role role = roleService.getByUuid(uuid);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<Page<Role>> getAllRoles(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info("Fetching roles with parameters: name={}, pageNumber={}, pageSize={}, order={}",
                name.orElse(""), pageNumber.orElse(Constant.INITIAL_PAGE), pageSize.orElse(Constant.PAGE_SIZE),
                order.orElse("asc"));

        Sort sortRequest;
        if (order.isPresent()) {
            if (order.get().equalsIgnoreCase("asc")) {
                sortRequest = Sort.by("name").ascending();
            } else {
                sortRequest = Sort.by("name").descending();
            }
        } else {
            sortRequest = Sort.by("name").ascending();
        }

        // Set up pageable variable
        Pageable pageable;
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            pageable = PageRequest.of(pageNumber.orElse(Constant.INITIAL_PAGE), pageSize.get(), sortRequest);
        } else {
            pageable = PageRequest.of(Constant.INITIAL_PAGE, Constant.PAGE_SIZE, sortRequest);
        }

        if (name.isPresent()) {
            return ResponseEntity.ok(roleService.searchByName(name.get(), pageable));
        }
        return ResponseEntity.ok(roleService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        log.info("Creating role with details: {}", roleDTO);
        Role createdRole = roleService.create(roleDTO.toRole());
        URI location = URI.create("/api/roles/" + createdRole.getUuid());
        return ResponseEntity.created(location).body(createdRole);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Role> updateRole(@PathVariable String uuid, @Valid @RequestBody RoleDTO roleDTO) {
        log.info("Updating role with UUID: {} and details: {}", uuid, roleDTO);
        Role updatedRole = roleService.update(uuid, roleDTO.toRole());
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteRole(@PathVariable String uuid) {
        log.info("Deleting role with UUID: {}", uuid);
        roleService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }

}
