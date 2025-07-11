package com.sys4business.sys4mech.services;

import com.querydsl.core.types.Predicate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.models.Employee;
import com.sys4business.sys4mech.repositories.EmployeeRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findEmployeeByUuid(String uuid) {
        return employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Employee not found with UUID: " + uuid));
    }

    public Page<Employee> findAllEmployees(Predicate predicate, Pageable pageable) {
        return employeeRepository.findAll(predicate, pageable);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAllByOrderByNameAsc();
    }
    
    public Employee create(Employee employee) {
        employee.setId(null); // Ensure a new employee is created
        employee.setUuid(Sys4MechUtil.generateUuid());
        return employeeRepository.save(employee);
    }

    public Employee update(String uuid, Employee employeeDetails) {
        Employee existingEmployee = findEmployeeByUuid(uuid);
        existingEmployee.setName(employeeDetails.getName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setAddressLine1(employeeDetails.getAddressLine1());
        existingEmployee.setAddressLine2(employeeDetails.getAddressLine2());
        existingEmployee.setCity(employeeDetails.getCity());
        existingEmployee.setCounty(employeeDetails.getCounty());
        existingEmployee.setPostalCode(employeeDetails.getPostalCode());
        existingEmployee.setCountry(employeeDetails.getCountry());
        existingEmployee.setDateOfBirth(employeeDetails.getDateOfBirth());
        existingEmployee.setPhone(employeeDetails.getPhone());
        return employeeRepository.save(existingEmployee);
    }

    public void delete(String uuid) {
        Employee employee = findEmployeeByUuid(uuid);
        employeeRepository.delete(employee);
    }

}
