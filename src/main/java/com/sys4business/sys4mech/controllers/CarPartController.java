package com.sys4business.sys4mech.controllers;

import java.net.URI;
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

import com.sys4business.sys4mech.models.CarPart;
import com.sys4business.sys4mech.models.Supplier;
import com.sys4business.sys4mech.models.dtos.CarPartDTO;
import com.sys4business.sys4mech.models.predicate.SetCarPartPredicate;
import com.sys4business.sys4mech.services.CarPartService;
import com.sys4business.sys4mech.services.SupplierService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/car-parts")
public class CarPartController {

    private static final Logger log = LoggerFactory.getLogger(CarPartController.class);

    @Autowired
    private CarPartService carPartService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('CAR_PARTS_READ')")
    public ResponseEntity<CarPart> getCarPartByUuid(@PathVariable String uuid) {
        log.info("Fetching car part with UUID: {}", uuid);
        CarPart carPart = carPartService.getByUuid(uuid);
        return ResponseEntity.ok(carPart);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CAR_PARTS_READ')")
    public ResponseEntity<Page<CarPart>> getAllCarParts(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> description,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info("Fetching car parts with parameters: field={}, value={}, pageNumber={}, pageSize={}, order={}",
                name.orElse(""), description.orElse(""), pageNumber.orElse(Constant.INITIAL_PAGE), pageSize.orElse(Constant.PAGE_SIZE),
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

        SetCarPartPredicate setCarPartPredicate = SetCarPartPredicate.builder()
                .name(name)
                .description(description)
                .build();
        
        return ResponseEntity.ok(carPartService.findAll(setCarPartPredicate.toPredicate(), pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAR_PARTS_WRITE')")
    public ResponseEntity<CarPart> create(@Valid @RequestBody CarPartDTO carPartDTO) {
        log.info("Creating car part: {}", carPartDTO.getName());
        Supplier supplier = supplierService.findSupplierByUuid(carPartDTO.getSupplierUuid());
        CarPart carPart = carPartDTO.toCarPart();
        carPart.setSupplier(supplier);
        carPart = carPartService.create(carPart);
        URI location = URI.create("/api/car-parts/" + carPart.getUuid());
        return ResponseEntity.created(location).body(carPart);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('CAR_PARTS_WRITE')")
    public ResponseEntity<CarPart> update(@PathVariable String uuid, @Valid @RequestBody CarPartDTO carPartDTO) {
        log.info("Updating user with UUID: {}", uuid);
        Supplier supplier = supplierService.findSupplierByUuid(carPartDTO.getSupplierUuid());
        CarPart carPart = carPartDTO.toCarPart();
        carPart.setSupplier(supplier);
        carPart = carPartService.update(uuid, carPart);
        return ResponseEntity.ok(carPart);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('CAR_PARTS_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable String uuid) {
        log.info("Deleting car part with UUID: {}", uuid);
        carPartService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
    
}
