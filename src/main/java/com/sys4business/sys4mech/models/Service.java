package com.sys4business.sys4mech.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service extends BaseEntity {

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "vat_rate")
    private BigDecimal vatRate;
    @Column(name = "electronic_diagnosis")
    private Boolean electronicDiagnosis;

}
