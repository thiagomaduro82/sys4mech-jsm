package com.sys4business.sys4mech.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.ServiceOrderServices;
import com.sys4business.sys4mech.models.dtos.ServiceOrderServicesDTO;
import com.sys4business.sys4mech.services.ServiceOrderServicesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service-order-services")
public class ServiceOrderServicesController {

    private final Logger log = LoggerFactory.getLogger(ServiceOrderServicesController.class);

    @Autowired
    private ServiceOrderServicesService serviceOrderServicesService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_READ')")
    public ResponseEntity<ServiceOrderServices> getById(@PathVariable Long id) {
        log.info("Fetching the service order service id: {}", id);
        return ResponseEntity.ok(serviceOrderServicesService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_READ')")
    public ResponseEntity<ServiceOrderServices> create(@Valid @RequestBody ServiceOrderServicesDTO serviceOrderServicesDTO) {
        log.info("Creating new service order services: {}, {}, {}, {}", serviceOrderServicesDTO.getServiceOrderUuid(), serviceOrderServicesDTO.getServiceUuid(), 
        serviceOrderServicesDTO.getAmount(), serviceOrderServicesDTO.getQuantity());
        return ResponseEntity.ok(serviceOrderServicesService.create(serviceOrderServicesService.convertDtoToEntity(serviceOrderServicesDTO)));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_WRITE')")
    public ResponseEntity<ServiceOrderServices> update(@PathVariable Long id, @Valid @RequestBody ServiceOrderServicesDTO serviceOrderServicesDTO) {
        log.info("Updating service order services id: {}", id);
        return ResponseEntity.ok(serviceOrderServicesService.update(serviceOrderServicesService.mapToUpdate(id, serviceOrderServicesDTO)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting service order services with id: {}", id);
        serviceOrderServicesService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
