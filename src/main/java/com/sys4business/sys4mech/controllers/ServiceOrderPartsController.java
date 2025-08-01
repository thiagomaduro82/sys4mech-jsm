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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.ServiceOrderParts;
import com.sys4business.sys4mech.models.dtos.ServiceOrderPartsDTO;
import com.sys4business.sys4mech.services.ServiceOrderPartsService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service-order-parts")
public class ServiceOrderPartsController {

    private final Logger log = LoggerFactory.getLogger(ServiceOrderPartsController.class);

    @Autowired
    private ServiceOrderPartsService serviceOrderPartsService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_READ')")
    public ResponseEntity<ServiceOrderParts> getById(@PathVariable Long id) {
        log.info("Fetching the service order parts id: {}", id);
        return ResponseEntity.ok(serviceOrderPartsService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_READ')")
    public ResponseEntity<ServiceOrderParts> create(@Valid @RequestBody ServiceOrderPartsDTO serviceOrderPartsDTO) {
        log.info("Creating new service order parts");
        return ResponseEntity.ok(serviceOrderPartsService.convertDtoToEntity(serviceOrderPartsDTO));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_WRITE')")
    public ResponseEntity<ServiceOrderParts> update(@PathVariable Long id, @Valid @RequestBody ServiceOrderPartsDTO serviceOrderPartsDTO) {
        log.info("Updating service order parts id: {}", id);
        return ResponseEntity.ok(serviceOrderPartsService.mapToUpdate(id, serviceOrderPartsDTO));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting service order parts with id: {}", id);
        serviceOrderPartsService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
