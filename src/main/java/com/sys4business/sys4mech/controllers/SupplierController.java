package com.sys4business.sys4mech.controllers;

import java.net.URI;
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

import com.sys4business.sys4mech.models.Supplier;
import com.sys4business.sys4mech.models.dtos.SupplierDTO;
import com.sys4business.sys4mech.models.predicate.SetSupplierPredicate;
import com.sys4business.sys4mech.services.SupplierService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final Logger log = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SUPPLIER_READ')")
    public ResponseEntity<Supplier> getSupplierByUuid(@PathVariable String uuid) {
        log.info("Fetching supplier with UUID: {}", uuid);
        Supplier supplier = supplierService.findSupplierByUuid(uuid);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPPLIER_READ')")
    public ResponseEntity<Page<Supplier>> getAllSuppliers(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> email,
            @RequestParam Optional<String> city,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info(
                "Fetching suppliers with filters - Name: {}, Email: {}, City: {}, Page Number: {}, Page Size: {}, Order: {}",
                name.orElse("N/A"), email.orElse("N/A"), city.orElse("N/A"),
                pageNumber.orElse(Constant.INITIAL_PAGE), pageSize.orElse(Constant.PAGE_SIZE),
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

        SetSupplierPredicate predicate = SetSupplierPredicate.builder()
                .name(name)
                .email(email)
                .city(city)
                .build();

        return ResponseEntity.ok(supplierService.findAllSuppliers(predicate.toPredicate(), pageable));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SUPPLIER_READ')")
    public ResponseEntity<List<Supplier>> getAllSupplierList() {
        log.info("Fetching all suppliers as a list");
        List<Supplier> suppliers = supplierService.findAllSupplier();
        return ResponseEntity.ok(suppliers);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPPLIER_WRITE')")
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        log.info("Creating employee with details: {}", supplierDTO);
        Supplier createdSupplier = supplierService.create(supplierDTO.toSupplier());
        URI location = URI.create("/api/suppliers/" + createdSupplier.getUuid());
        return ResponseEntity.created(location).body(createdSupplier);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SUPPLIER_WRITE')")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable String uuid,
            @Valid @RequestBody SupplierDTO supplierDTO) {
        log.info("Updating supplier with UUID: {} and details: {}", uuid, supplierDTO);
        Supplier updatedSupplier = supplierService.update(uuid, supplierDTO.toSupplier());
        return ResponseEntity.ok(updatedSupplier);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SUPPLIER_DELETE')")
    public ResponseEntity<Void> deleteSupplier(@PathVariable String uuid) {
        log.info("Deleting supplier with UUID: {}", uuid);
        supplierService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
    
}
