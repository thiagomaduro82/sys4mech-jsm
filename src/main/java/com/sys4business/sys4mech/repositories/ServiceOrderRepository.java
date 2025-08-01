package com.sys4business.sys4mech.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.ServiceOrder;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    
    Optional<ServiceOrder> findByUuid(String uuid);
    
}
