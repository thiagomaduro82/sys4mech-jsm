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

import com.sys4business.sys4mech.models.Employee;
import com.sys4business.sys4mech.models.dtos.EmployeeDTO;
import com.sys4business.sys4mech.models.predicate.SetEmployeePredicate;
import com.sys4business.sys4mech.services.EmployeeService;
import com.sys4business.sys4mech.utils.Constant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable String uuid) {
        log.info("Fetching employee with UUID: {}", uuid);
        Employee employee = employeeService.findEmployeeByUuid(uuid);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> email,
            @RequestParam Optional<String> city,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> order) {

        log.info(
                "Fetching employees with filters - Name: {}, Email: {}, City: {}, Page Number: {}, Page Size: {}, Order: {}",
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

        SetEmployeePredicate predicate = SetEmployeePredicate.builder()
                .name(name)
                .email(email)
                .city(city)
                .build();

        return ResponseEntity.ok(employeeService.findAllEmployees(predicate.toPredicate(), pageable));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('EMPLOYEE_READ')")
    public ResponseEntity<List<Employee>> getAllEmployeeList() {
        log.info("Fetching all employees as a list");
        List<Employee> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_WRITE')")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Creating employee with details: {}", employeeDTO);
        Employee createdEmployee = employeeService.create(employeeDTO.toEmployee());
        URI location = URI.create("/api/employees/" + createdEmployee.getUuid());
        return ResponseEntity.created(location).body(createdEmployee);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('EMPLOYEE_WRITE')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String uuid,
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Updating employee with UUID: {} and details: {}", uuid, employeeDTO);
        Employee updatedEmployee = employeeService.update(uuid, employeeDTO.toEmployee());
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('EMPLOYEE_DELETE')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String uuid) {
        log.info("Deleting employee with UUID: {}", uuid);
        employeeService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}
