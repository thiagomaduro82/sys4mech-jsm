package com.sys4business.sys4mech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.sys4business.sys4mech.exceptions.ObjectNotFoundException;
import com.sys4business.sys4mech.models.CarPart;
import com.sys4business.sys4mech.repositories.CarPartRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class CarPartService {

    @Autowired
    private CarPartRepository carPartRepository;

    public CarPart getByUuid(String uuid) {
        return carPartRepository.findByUuid(uuid).orElseThrow(
            () -> new ObjectNotFoundException("Car part not found with UUID: " + uuid)
        );
    }

    public CarPart create(CarPart carPart) {
        carPart.setId(null); // Ensure a new entity is created
        carPart.setUuid(Sys4MechUtil.generateUuid());
        return carPartRepository.save(carPart);
    }

    public void delete(String uuid) {
        CarPart carPart = getByUuid(uuid);
        carPartRepository.delete(carPart);
    }

    public CarPart update(String uuid, CarPart carPart) {
        CarPart existingCarPart = getByUuid(uuid);
        existingCarPart.setName(carPart.getName());
        existingCarPart.setDescription(carPart.getDescription());
        existingCarPart.setCostPrice(carPart.getCostPrice());
        existingCarPart.setSellingPrice(carPart.getSellingPrice());
        existingCarPart.setVatRate(carPart.getVatRate());
        existingCarPart.setBarcode(carPart.getBarcode());
        existingCarPart.setStockQuantity(carPart.getStockQuantity());
        existingCarPart.setMinStockQuantity(carPart.getMinStockQuantity());
        return carPartRepository.save(existingCarPart);
    }

    public Page<CarPart> findAll(Predicate predicate, Pageable pageable) {
        return carPartRepository.findAll(predicate, pageable);
    }

    public List<CarPart> findAll() {
        return carPartRepository.findAll();
    }
    
}
