package com.sys4business.sys4mech.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sys4business.sys4mech.models.Role;
import com.sys4business.sys4mech.models.User;
import com.sys4business.sys4mech.repositories.UserRepository;
import com.sys4business.sys4mech.services.RoleService;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Component
public class DataSeeder implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        log.info("Starting the user seed...");
        Role role = roleService.getAllList().get(0);
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUuid(Sys4MechUtil.generateUuid());
            user.setName("Admin");
            user.setEmail("admin@sys4mech.com");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRole(role);
            userRepository.save(user);
            log.info("Users successfully inserted!");
        } else {
            log.warn("Existing users. Seed ignored.");
        }
    }
    
}
