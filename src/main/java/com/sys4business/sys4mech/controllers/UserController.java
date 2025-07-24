package com.sys4business.sys4mech.controllers;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.User;
import com.sys4business.sys4mech.models.dtos.ChangePassword;
import com.sys4business.sys4mech.models.dtos.UserAddDTO;
import com.sys4business.sys4mech.models.dtos.UserUpdDTO;
import com.sys4business.sys4mech.services.RoleService;
import com.sys4business.sys4mech.services.UserService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final RoleService roleService;
    private final UserService userService;

    public UserController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<User> getUserByUuid(@PathVariable String uuid) {
        log.info("Fetching user with UUID: {}", uuid);
        User user = userService.getByUuid(uuid);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam Optional<String> field,
            @RequestParam Optional<String> value,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info("Fetching users with parameters: field={}, value={}, pageNumber={}, pageSize={}, order={}",
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
                return ResponseEntity.ok(userService.searchByName(value.get(), pageable));
            }
            return ResponseEntity.ok(userService.searchByEmail(value.get(), pageable));
        }
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserAddDTO userAddDTO) {
        log.info("Creating user: {}", userAddDTO.getName());
        User user = userService.create(convertAddToUser(userAddDTO));
        URI location = URI.create("/api/users/" + user.getUuid());
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<User> updateUser(@PathVariable String uuid, @Valid @RequestBody UserUpdDTO userUpdDTO) {
        log.info("Updating user with UUID: {}", uuid);
        User updatedUser = convertUpdateToUser(userUpdDTO);
        User user = userService.update(uuid, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{uuid}/change-password")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<User> changePassword(@PathVariable String uuid, @Valid @RequestBody ChangePassword changePassword) {
        log.info("Changing password for user with UUID: {}", uuid);
        User user = userService.changePassword(uuid, changePassword);
        return ResponseEntity.ok(user);
    }

    private User convertAddToUser(UserAddDTO userAddDTO) {
        User user = new User();
        user.setName(userAddDTO.getName());
        user.setEmail(userAddDTO.getEmail());
        user.setPassword(userAddDTO.getPassword());
        user.setRole(roleService.getByUuid(userAddDTO.getRoleUuid()));
        return user;
    }

    private User convertUpdateToUser(UserUpdDTO userUpdDTO) {
        User user = new User();
        user.setName(userUpdDTO.getName());
        user.setEmail(userUpdDTO.getEmail());
        user.setRole(roleService.getByUuid(userUpdDTO.getRoleUuid()));
        return user;
    }

}
