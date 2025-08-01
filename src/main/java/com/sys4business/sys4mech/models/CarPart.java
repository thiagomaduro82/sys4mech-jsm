package com.sys4business.sys4mech.models;

import java.math.BigDecimal;

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
@Table(name = "car_parts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarPart extends BaseEntity {

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "cost_price")
    private BigDecimal costPrice;
    @Column(name = "selling_price")
    private BigDecimal sellingPrice;
    @Column(name = "vat_rate")
    private BigDecimal vatRate;
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "stock_quantity")
    private int stockQuantity;
    @Column(name = "min_stock_quantity")
    private int minStockQuantity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    

}
