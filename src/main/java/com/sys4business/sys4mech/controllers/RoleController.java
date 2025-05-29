package com.sys4business.sys4mech.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.Role;
import com.sys4business.sys4mech.models.predicate.SetRolePredicate;
import com.sys4business.sys4mech.services.RoleService;
import com.sys4business.sys4mech.utils.Constant;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    public ResponseEntity<Page<Role>> getAllRoles(
            Optional<String> uuid,
            Optional<String> name,
            Optional<Integer> pageNumber,
            Optional<Integer> pageSize,
            Optional<String> sort,
            Optional<String> order) {

        Sort sortRequest;
        if (sort.isPresent() && order.isPresent()) {
            if (order.get().equalsIgnoreCase("asc")) {
                sortRequest = Sort.by(sort.get()).ascending();
            } else {
                sortRequest = Sort.by(sort.get()).descending();
            }
        } else {
            sortRequest = Sort.unsorted();
        }
        // Set up pageable variable
        Pageable pageable;
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            pageable = PageRequest.of(pageNumber.orElse(Constant.INITIAL_PAGE), pageSize.get(), sortRequest);
        } else {
            pageable = PageRequest.of(Constant.INITIAL_PAGE, Constant.PAGE_SIZE, sortRequest);
        }
        // Set the predicate
        SetRolePredicate setRolePredicate = SetRolePredicate.builder()
                .uuid(uuid)
                .name(name)
                .build();
        // Implementation will go here
        return ResponseEntity.ok(roleService.findAll(setRolePredicate.toPredicate(), pageable));
    }

}
