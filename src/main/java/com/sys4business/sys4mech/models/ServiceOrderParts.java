package com.sys4business.sys4mech.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_order_parts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderParts extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_order_id")
    @JsonIgnore
    private ServiceOrder serviceOrder;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_part_id")
    private CarPart carPart;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "amount")
    private BigDecimal amount;

}
