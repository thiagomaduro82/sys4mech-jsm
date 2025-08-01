package com.sys4business.sys4mech.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.CarPart;
import com.sys4business.sys4mech.models.ServiceOrder;
import com.sys4business.sys4mech.models.ServiceOrderParts;
import com.sys4business.sys4mech.models.dtos.ServiceOrderPartsDTO;
import com.sys4business.sys4mech.repositories.ServiceOrderPartsRepository;

@Service
public class ServiceOrderPartsService {

    @Autowired
    private ServiceOrderPartsRepository serviceOrderPartsRepository;

    @Autowired
    private CarPartService carPartService;

    @Autowired
    private ServiceOrderService serviceOrderService;

    public ServiceOrderParts getById(Long id) {
        return serviceOrderPartsRepository.findById(id).orElseThrow(() ->
        new ObjectNotFoundException("Service order parts not found with ID: " + id));
    }

    public ServiceOrderParts create(ServiceOrderParts serviceOrderParts) {
        serviceOrderParts.setId(null);
        return serviceOrderPartsRepository.save(serviceOrderParts);
    }

    public ServiceOrderParts update(ServiceOrderParts serviceOrderParts) {
        return serviceOrderPartsRepository.save(serviceOrderParts);
    }

    public void delete(Long id) {
        ServiceOrderParts serviceOrderParts = getById(id);
        serviceOrderPartsRepository.delete(serviceOrderParts);
    }

    public ServiceOrderParts convertDtoToEntity(ServiceOrderPartsDTO serviceOrderPartsDTO) {
        ServiceOrder serviceOrder = serviceOrderService.getByUuid(serviceOrderPartsDTO.getServiceOrderUuid());
        CarPart carPart = carPartService.getByUuid(serviceOrderPartsDTO.getCarPartUuid());
        ServiceOrderParts serviceOrderParts = new ServiceOrderParts();
        serviceOrderParts.setServiceOrder(serviceOrder);
        serviceOrderParts.setCarPart(carPart);
        serviceOrderParts.setQuantity(serviceOrderPartsDTO.getQuantity());
        serviceOrderParts.setAmount(serviceOrderPartsDTO.getAmount());
        return serviceOrderParts;
    }

    public ServiceOrderParts mapToUpdate(Long id, ServiceOrderPartsDTO serviceOrderPartsDTO) {
        ServiceOrder serviceOrder = serviceOrderService.getByUuid(serviceOrderPartsDTO.getServiceOrderUuid());
        CarPart carPart = carPartService.getByUuid(serviceOrderPartsDTO.getCarPartUuid());
        ServiceOrderParts serviceOrderParts = getById(id);
        serviceOrderParts.setServiceOrder(serviceOrder);
        serviceOrderParts.setCarPart(carPart);
        serviceOrderParts.setQuantity(serviceOrderPartsDTO.getQuantity());
        serviceOrderParts.setAmount(serviceOrderPartsDTO.getAmount());
        return serviceOrderParts;
    }
    
}
