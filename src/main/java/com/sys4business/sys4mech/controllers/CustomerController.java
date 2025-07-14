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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sys4business.sys4mech.models.Customer;
import com.sys4business.sys4mech.models.dtos.CustomerDTO;
import com.sys4business.sys4mech.models.predicate.SetCustomerPredicate;
import com.sys4business.sys4mech.services.CustomerService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{uuid}")
    public ResponseEntity<Customer> getCustomerByUuid(@PathVariable String uuid) {
        log.info("Fetching customer with UUID: {}", uuid);
        Customer customer = customerService.findCustomerByUuid(uuid);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomers(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> email,
            @RequestParam Optional<String> city,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info(
                "Fetching customers with filters - Name: {}, Email: {}, City: {}, Page Number: {}, Page Size: {}, Order: {}",
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

        SetCustomerPredicate predicate = SetCustomerPredicate.builder()
                .name(name)
                .email(email)
                .city(city)
                .build();

        return ResponseEntity.ok(customerService.findAllCustomers(predicate.toPredicate(), pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> getAllCustomerList() {
        log.info("Fetching all customers as a list");
        List<Customer> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("Creating customer with details: {}", customerDTO);
        Customer createdCustomer = customerService.create(customerDTO.toCustomer());
        URI location = URI.create("/api/customers/" + createdCustomer.getUuid());
        return ResponseEntity.created(location).body(createdCustomer);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String uuid,
            @Valid @RequestBody CustomerDTO customerDTO) {
        log.info("Updating customer with UUID: {} and details: {}", uuid, customerDTO);
        Customer updatedCustomer = customerService.update(uuid, customerDTO.toCustomer());
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String uuid) {
        log.info("Deleting customer with UUID: {}", uuid);
        customerService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
    
}
