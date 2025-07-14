package com.sys4business.sys4mech.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, QuerydslPredicateExecutor<Customer> {

    Optional<Customer> findByUuid(String uuid);

    List<Customer> findAllByOrderByNameAsc();
    
}
