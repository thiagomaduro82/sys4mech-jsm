package com.sys4business.sys4mech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends BaseEntity {

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "contact_name", nullable = false)
    private String contactName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "address_line1", nullable = false)
    private String addressLine1;
    @Column(name = "address_line2")
    private String addressLine2;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "county")
    private String county;
    @Column(name = "postal_code", nullable = false)
    private String postalCode;
    @Column(name = "country", nullable = false)
    private String country;
    
}
