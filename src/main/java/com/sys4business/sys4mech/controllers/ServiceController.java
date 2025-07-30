package com.sys4business.sys4mech.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.sys4business.sys4mech.models.Service;
import com.sys4business.sys4mech.models.dtos.ServiceDTO;
import com.sys4business.sys4mech.services.ServiceService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final Logger log = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_READ')")
    public ResponseEntity<Service> getServiceByUuid(@PathVariable String uuid) {
        log.info("Fetching service with UUID: {}", uuid);
        return ResponseEntity.ok(serviceService.getServiceByUuid(uuid));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SERVICE_READ')")
    public ResponseEntity<Page<Service>> getAllServices(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info("Fetching all services with filters: name={}, description={}, pageNumber={}, pageSize={}, order={}",
                name.orElse(""), pageNumber.orElse(0), pageSize.orElse(10), order.orElse(""));

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
            return ResponseEntity.ok(serviceService.getAllServices(name.get(), pageable));
        } else {
            return ResponseEntity.ok(serviceService.getAllServices(null, pageable));
        }
        
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SERVICE_READ')")
    public ResponseEntity<List<Service>> getAllServices() {
        log.info("Fetching all services");
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SERVICE_WRITE')")
    public ResponseEntity<Service> createService(@Valid @RequestBody ServiceDTO serviceDTO) {
        log.info("Creating new service: {}", serviceDTO.getName());
        return ResponseEntity.ok(serviceService.createService(serviceDTO.toService()));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_WRITE')")
    public ResponseEntity<Service> updateService(@PathVariable String uuid, @Valid @RequestBody ServiceDTO serviceDTO) {
        log.info("Updating service with UUID: {}", uuid);
        return ResponseEntity.ok(serviceService.updateService(uuid, serviceDTO.toService()));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_DELETE')")
    public ResponseEntity<Void> deleteService(@PathVariable String uuid) {
        log.info("Deleting service with UUID: {}", uuid);
        serviceService.deleteService(uuid);
        return ResponseEntity.noContent().build();
    }
    
}
