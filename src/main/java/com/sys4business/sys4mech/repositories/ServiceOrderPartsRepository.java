package com.sys4business.sys4mech.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sys4business.sys4mech.models.ServiceOrderParts;

public interface ServiceOrderPartsRepository extends JpaRepository<ServiceOrderParts, Long> {

    Optional<ServiceOrderParts> findByUuid(String uuid);
    
}
