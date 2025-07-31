package com.sys4business.sys4mech.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, QuerydslPredicateExecutor<Supplier> {

    Optional<Supplier> findByUuid(String uuid);

    List<Supplier> findAllByOrderByNameAsc();

}
