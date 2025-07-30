package com.sys4business.sys4mech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.repositories.ServiceRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public com.sys4business.sys4mech.models.Service getServiceByUuid(String uuid) {
        return serviceRepository.findByUuid(uuid).orElseThrow(() -> new ObjectNotFoundException("Service not found with UUID: " + uuid));
    }

    public Page<com.sys4business.sys4mech.models.Service> getAllServices(String name, Pageable pageable) {
        if (name == null || name.isEmpty()) {
            return serviceRepository.findAll(pageable);
        } else {
            return serviceRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
    }

    public List<com.sys4business.sys4mech.models.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public com.sys4business.sys4mech.models.Service createService(com.sys4business.sys4mech.models.Service service) {
        service.setId(null);
        service.setUuid(Sys4MechUtil.generateUuid());
        return serviceRepository.save(service);
    }

    public com.sys4business.sys4mech.models.Service updateService(String uuid, com.sys4business.sys4mech.models.Service service) {
        com.sys4business.sys4mech.models.Service updatedService = getServiceByUuid(uuid);
        updatedService.setName(service.getName());
        updatedService.setAmount(service.getAmount());
        updatedService.setVatRate(service.getVatRate());
        updatedService.setElectronicDiagnosis(service.getElectronicDiagnosis());
        return serviceRepository.save(updatedService);
    }

    public void deleteService(String uuid) {
        com.sys4business.sys4mech.models.Service service = getServiceByUuid(uuid);
        serviceRepository.delete(service);
    }
    
}
