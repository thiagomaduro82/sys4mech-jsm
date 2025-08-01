package com.sys4business.sys4mech.controllers;

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

import com.sys4business.sys4mech.models.ServiceOrder;
import com.sys4business.sys4mech.models.dtos.ServiceOrderDTO;
import com.sys4business.sys4mech.services.ServiceOrderService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service-orders")
public class ServiceOrderController {

    private final Logger log = LoggerFactory.getLogger(ServiceOrderController.class);

    @Autowired
    private ServiceOrderService serviceOrderService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_READ')")
    public ResponseEntity<ServiceOrder> getByUuid(@PathVariable String uuid) {
        log.info("Fetching service order with UUID: {}", uuid);
        return ResponseEntity.ok(serviceOrderService.getByUuid(uuid));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_READ')")
    public ResponseEntity<Page<ServiceOrder>> getAll(
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info("Fetching all service order with filters: description={}, pageNumber={}, pageSize={}, order={}",
                pageNumber.orElse(0), pageSize.orElse(10), order.orElse(""));

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

        return ResponseEntity.ok(serviceOrderService.getAll(pageable));

    }

    @PostMapping
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_WRITE')")
    public ResponseEntity<ServiceOrder> create(@Valid @RequestBody ServiceOrderDTO serviceOrderDTO) {
        log.info("Creating new service order");
        return ResponseEntity.ok(serviceOrderService.convertDtoToEntity(serviceOrderDTO));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_WRITE')")
    public ResponseEntity<ServiceOrder> update(@PathVariable String uuid, @Valid @RequestBody ServiceOrderDTO serviceOrderDTO) {
        log.info("Updating service order", uuid);
        return ResponseEntity.ok(serviceOrderService.mapToUpdate(uuid, serviceOrderDTO));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SERVICE_ORDERS_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        log.info("Deleting service order with UUID: {}", uuid);
        serviceOrderService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}
