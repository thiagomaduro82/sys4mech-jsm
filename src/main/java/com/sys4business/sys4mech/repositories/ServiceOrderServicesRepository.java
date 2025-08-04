package com.sys4business.sys4mech.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.ServiceOrderServices;

@Repository
public interface ServiceOrderServicesRepository extends JpaRepository<ServiceOrderServices, Long> {

}
