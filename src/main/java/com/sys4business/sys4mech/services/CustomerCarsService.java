package com.sys4business.sys4mech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.CustomerCars;
import com.sys4business.sys4mech.repositories.CustomerCarsRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class CustomerCarsService {

    @Autowired
    private CustomerCarsRepository customerCarsRepository;

    public CustomerCars getByUuid(String uuid) {
        return customerCarsRepository.findByUuid(uuid).orElseThrow(
            () -> new ObjectNotFoundException("Customer car not found with UUID: " + uuid)
        );
    }

    public CustomerCars create(CustomerCars customerCar) {
        customerCar.setId(null); // Ensure a new entity is created
        customerCar.setUuid(Sys4MechUtil.generateUuid());
        return customerCarsRepository.save(customerCar);
    }

    public void delete(String uuid) {
        CustomerCars customerCar = getByUuid(uuid);
        customerCarsRepository.delete(customerCar);
    }

    public CustomerCars update(String uuid, CustomerCars customerCar) {
        CustomerCars existingCustomerCar = getByUuid(uuid);
        existingCustomerCar.setMake(customerCar.getMake());
        existingCustomerCar.setModel(customerCar.getModel());
        existingCustomerCar.setYear(customerCar.getYear());
        existingCustomerCar.setRegistrationNumber(customerCar.getRegistrationNumber());
        existingCustomerCar.setColor(customerCar.getColor());
        existingCustomerCar.setVin(customerCar.getVin());
        existingCustomerCar.setCustomer(customerCar.getCustomer()); // Assuming customer is set correctly
        return customerCarsRepository.save(existingCustomerCar);
    }

    public Page<CustomerCars> findAll(Predicate predicate, Pageable pageable) {
        return customerCarsRepository.findAll(predicate, pageable);
    }

    public List<CustomerCars> findAll() {
        return customerCarsRepository.findAll();
    }
    
}
