package com.sys4business.sys4mech.controllers;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.CustomerCars;
import com.sys4business.sys4mech.models.dtos.CustomerCarsDTO;
import com.sys4business.sys4mech.services.CustomerCarsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer-cars")
public class CustomerCarsController {

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerCarsService customerCarsService;

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerCars> getCustomerCarByUuid(@PathVariable String uuid) {
        log.info("Fetching customer car");
        CustomerCars customerCar = customerCarsService.getByUuid(uuid);
        return ResponseEntity.ok(customerCar);
    }

    @GetMapping
    public ResponseEntity<List<CustomerCars>> getAllCustomerCars() {
        log.info("Fetching all customer cars as a list");
        List<CustomerCars> customerCars = customerCarsService.findAll();
        return ResponseEntity.ok(customerCars);
    }

    @PostMapping
    public ResponseEntity<CustomerCars> createCustomerCar(@Valid @RequestBody CustomerCarsDTO customerCarDTO) {
        log.info("Creating new customer car");
        CustomerCars createdCustomerCar = customerCarsService.create(customerCarDTO.toCustomerCars());
        URI location = URI.create("/api/customer-cars/" + createdCustomerCar.getUuid());
        return ResponseEntity.created(location).body(createdCustomerCar);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CustomerCars> updateCustomerCar(String uuid, @Valid @RequestBody CustomerCarsDTO customerCarDTO) {
        log.info("Updating customer car with UUID: {}", uuid);
        CustomerCars updatedCustomerCar = customerCarsService.update(uuid, customerCarDTO.toCustomerCars());
        return ResponseEntity.ok(updatedCustomerCar);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCustomerCar(@PathVariable String uuid) {
        log.info("Deleting customer car with UUID: {}", uuid);
        customerCarsService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
    
}
