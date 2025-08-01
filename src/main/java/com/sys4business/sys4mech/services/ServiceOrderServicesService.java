package com.sys4business.sys4mech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.ServiceOrder;
import com.sys4business.sys4mech.models.ServiceOrderServices;
import com.sys4business.sys4mech.models.dtos.ServiceOrderServicesDTO;
import com.sys4business.sys4mech.repositories.ServiceOrderServicesRepository;

@Service
public class ServiceOrderServicesService {

    @Autowired
    private ServiceOrderServicesRepository serviceOrderServicesRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceOrderService serviceOrderService;

    public ServiceOrderServices getById(Long id) {
        return serviceOrderServicesRepository.findById(id).orElseThrow(() ->
        new ObjectNotFoundException("Service Order not found with UUID: " + id));
    }

    public ServiceOrderServices create(ServiceOrderServices serviceOrderServices) {
        serviceOrderServices.setId(null);
        return serviceOrderServicesRepository.save(serviceOrderServices);
    }

    public ServiceOrderServices update(ServiceOrderServices serviceOrderServices) {
        return serviceOrderServicesRepository.save(serviceOrderServices);
    }

    public void delete(Long id) {
        ServiceOrderServices serviceOrderServices = getById(id);
        serviceOrderServicesRepository.delete(serviceOrderServices);
    }

    public ServiceOrderServices convertDtoToEntity(ServiceOrderServicesDTO serviceOrderServicesDTO) {
        ServiceOrder serviceOrder = serviceOrderService.getByUuid(serviceOrderServicesDTO.getServiceOrderUuid());
        com.sys4business.sys4mech.models.Service service = serviceService.getServiceByUuid(serviceOrderServicesDTO.getServiceUuid());
        ServiceOrderServices serviceOrderServices = new ServiceOrderServices();
        serviceOrderServices.setServiceOrder(serviceOrder);
        serviceOrderServices.setService(service);
        serviceOrderServices.setQuantity(serviceOrderServicesDTO.getQuantity());
        serviceOrderServices.setAmount(serviceOrderServicesDTO.getAmount());
        return serviceOrderServices;
    }

    public ServiceOrderServices mapToUpdate(Long id, ServiceOrderServicesDTO serviceOrderServicesDTO) {
        ServiceOrder serviceOrder = serviceOrderService.getByUuid(serviceOrderServicesDTO.getServiceOrderUuid());
        com.sys4business.sys4mech.models.Service service = serviceService.getServiceByUuid(serviceOrderServicesDTO.getServiceUuid());
        ServiceOrderServices serviceOrderServices = getById(id);
        serviceOrderServices.setServiceOrder(serviceOrder);
        serviceOrderServices.setService(service);
        serviceOrderServices.setQuantity(serviceOrderServicesDTO.getQuantity());
        serviceOrderServices.setAmount(serviceOrderServicesDTO.getAmount());
        return serviceOrderServices;
    }

    
}
