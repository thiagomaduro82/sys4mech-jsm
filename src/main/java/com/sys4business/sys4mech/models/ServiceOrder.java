package com.sys4business.sys4mech.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sys4business.sys4mech.models.enums.ServiceOrderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrder extends BaseEntity {

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_car_id")
    private CustomerCars customerCar;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServiceOrderEnum status;
    @Column(name = "work_required", nullable = false)
    private String workRequired;
    @Column(name = "observations")
    private String observations;
    @OneToMany(mappedBy = "serviceOrder")
    @JsonManagedReference
    private List<ServiceOrderParts> serviceOrderParts = new ArrayList<>();
    @OneToMany(mappedBy = "serviceOrder")
    @JsonManagedReference
    private List<ServiceOrderServices> serviceOrderServices = new ArrayList<>();

    
}
