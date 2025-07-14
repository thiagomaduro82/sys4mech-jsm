package com.sys4business.sys4mech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.sys4business.sys4mech.models.Customer;
import com.sys4business.sys4mech.repositories.CustomerRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findCustomerByUuid(String uuid) {
        return customerRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Customer not found with UUID: " + uuid));
    }

    public Page<Customer> findAllCustomers(Predicate predicate, Pageable pageable) {
        return customerRepository.findAll(predicate, pageable);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAllByOrderByNameAsc();
    }
    
    public Customer create(Customer customer) {
        customer.setId(null); // Ensure a new employee is created
        customer.setUuid(Sys4MechUtil.generateUuid());
        return customerRepository.save(customer);
    }

    public Customer update(String uuid, Customer customerDetails) {
        Customer existingCustomer = findCustomerByUuid(uuid);
        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setAddressLine1(customerDetails.getAddressLine1());
        existingCustomer.setAddressLine2(customerDetails.getAddressLine2());
        existingCustomer.setCity(customerDetails.getCity());
        existingCustomer.setCounty(customerDetails.getCounty());
        existingCustomer.setPostalCode(customerDetails.getPostalCode());
        existingCustomer.setCountry(customerDetails.getCountry());
        existingCustomer.setDateOfBirth(customerDetails.getDateOfBirth());
        existingCustomer.setPhone(customerDetails.getPhone());
        return customerRepository.save(existingCustomer);
    }

    public void delete(String uuid) {
        Customer customer = findCustomerByUuid(uuid);
        customerRepository.delete(customer);
    }
    
}
