package com.sys4business.sys4mech.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {

    Optional<Employee> findByUuid(String uuid);

    List<Employee> findAllByOrderByNameAsc();
    
}
