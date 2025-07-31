package com.sys4business.sys4mech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.sys4business.sys4mech.models.Supplier;
import com.sys4business.sys4mech.repositories.SupplierRepository;
import com.sys4business.sys4mech.utils.Sys4MechUtil;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier findSupplierByUuid(String uuid) {
        return supplierRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Supplier not found with UUID: " + uuid));
    }

    public Page<Supplier> findAllSuppliers(Predicate predicate, Pageable pageable) {
        return supplierRepository.findAll(predicate, pageable);
    }

    public List<Supplier> findAllSupplier() {
        return supplierRepository.findAllByOrderByNameAsc();
    }
    
    public Supplier create(Supplier supplier) {
        supplier.setId(null); 
        supplier.setUuid(Sys4MechUtil.generateUuid());
        return supplierRepository.save(supplier);
    }

    public Supplier update(String uuid, Supplier supplierDetails) {
        Supplier existingSupplier = findSupplierByUuid(uuid);
        existingSupplier.setName(supplierDetails.getName());
        existingSupplier.setContactName(supplierDetails.getContactName());
        existingSupplier.setEmail(supplierDetails.getEmail());
        existingSupplier.setAddressLine1(supplierDetails.getAddressLine1());
        existingSupplier.setAddressLine2(supplierDetails.getAddressLine2());
        existingSupplier.setCity(supplierDetails.getCity());
        existingSupplier.setCounty(supplierDetails.getCounty());
        existingSupplier.setPostalCode(supplierDetails.getPostalCode());
        existingSupplier.setCountry(supplierDetails.getCountry());
        existingSupplier.setPhone(supplierDetails.getPhone());
        return supplierRepository.save(existingSupplier);
    }

    public void delete(String uuid) {
        Supplier supplier = findSupplierByUuid(uuid);
        supplierRepository.delete(supplier);
    }
    
}
