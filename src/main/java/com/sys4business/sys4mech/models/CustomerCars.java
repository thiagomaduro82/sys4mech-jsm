package com.sys4business.sys4mech.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_cars")
public class CustomerCars extends BaseEntity {

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;
    @Column(name = "make", nullable = false)
    private String make;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "year", nullable = false)
    private Integer year;
    @Column(name = "color", nullable = false)
    private String color;
    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;
    @Column(name = "vin", nullable = false, unique = true)
    private String vin;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    public CustomerCars() {
        super();
    }

    public CustomerCars(String uuid, String make, String model, Integer year, String color,
                        String registrationNumber, String vin, Customer customer) {
        this.uuid = uuid;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.registrationNumber = registrationNumber;
        this.vin = vin;
        this.customer = customer;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    
    
}
