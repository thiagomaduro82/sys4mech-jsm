package com.sys4business.sys4mech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.Customer;
import com.sys4business.sys4mech.models.CustomerCars;
import com.sys4business.sys4mech.models.Employee;
import com.sys4business.sys4mech.models.ServiceOrder;
import com.sys4business.sys4mech.models.dtos.ServiceOrderDTO;
import com.sys4business.sys4mech.models.enums.ServiceOrderEnum;
import com.sys4business.sys4mech.repositories.ServiceOrderRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class ServiceOrderService {

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerCarsService customerCarsService;

    @Autowired
    private EmployeeService employeeService;

    public ServiceOrder getByUuid(String uuid) {
        return serviceOrderRepository.findByUuid(uuid).orElseThrow(() -> 
            new ObjectNotFoundException("Service Order not found with UUID: " + uuid));
    }

    public Page<ServiceOrder> getAll(Pageable pageable) {
        return serviceOrderRepository.findAll(pageable);
    }

    public ServiceOrder create(ServiceOrder serviceOrder) {
        serviceOrder.setId(null);
        serviceOrder.setUuid(Sys4MechUtil.generateUuid());
        return serviceOrderRepository.save(serviceOrder);
    }

    public ServiceOrder update(ServiceOrder serviceOrder) {
        if (serviceOrder.getId() == null) {
            throw new ObjectNotFoundException("Service Order ID must not be null for update");
        }
        return serviceOrderRepository.save(serviceOrder);
    }

    public void delete(String uuid) {
        ServiceOrder serviceOrder = getByUuid(uuid);
        serviceOrderRepository.delete(serviceOrder);
    }

    public ServiceOrder convertDtoToEntity(ServiceOrderDTO serviceOrderDTO) {
        Customer customer = customerService.findCustomerByUuid(serviceOrderDTO.getCustomerUuid());
        CustomerCars customerCar = customerCarsService.getByUuid(serviceOrderDTO.getCustomerCarUuid());
        Employee employee = employeeService.findEmployeeByUuid(serviceOrderDTO.getEmployeeUuid());
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setCustomer(customer);
        serviceOrder.setCustomerCar(customerCar);
        serviceOrder.setEmployee(employee);
        serviceOrder.setWorkRequired(serviceOrderDTO.getWorkRequired());
        serviceOrder.setObservations(serviceOrderDTO.getObservations());
        serviceOrder.setStatus(ServiceOrderEnum.valueOf(serviceOrderDTO.getStatus()));
        return serviceOrder;
    }

    public ServiceOrder mapToUpdate(String uuid, ServiceOrderDTO serviceOrderDTO) {
        Customer customer = customerService.findCustomerByUuid(serviceOrderDTO.getCustomerUuid());
        CustomerCars customerCar = customerCarsService.getByUuid(serviceOrderDTO.getCustomerCarUuid());
        Employee employee = employeeService.findEmployeeByUuid(serviceOrderDTO.getEmployeeUuid());
        ServiceOrder serviceOrder = getByUuid(uuid);
        serviceOrder.setCustomer(customer);
        serviceOrder.setCustomerCar(customerCar);
        serviceOrder.setEmployee(employee);
        serviceOrder.setWorkRequired(serviceOrderDTO.getWorkRequired());
        serviceOrder.setObservations(serviceOrderDTO.getObservations());
        serviceOrder.setStatus(ServiceOrderEnum.valueOf(serviceOrderDTO.getStatus()));
        return serviceOrder;
    }
    
}
