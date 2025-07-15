package com.sys4business.sys4mech.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.CustomerCars;

@Repository
public interface CustomerCarsRepository extends JpaRepository<CustomerCars, Long>, QuerydslPredicateExecutor<CustomerCars> {

    Optional<CustomerCars> findByUuid(String uuid);
    
} 
