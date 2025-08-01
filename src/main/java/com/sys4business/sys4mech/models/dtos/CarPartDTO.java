package com.sys4business.sys4mech.models.dtos;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.sys4business.sys4mech.models.CarPart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarPartDTO {
    
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 1, max = 100)
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 1, max = 255)
    private String description;

    private BigDecimal costPrice;

    private BigDecimal sellingPrice;

    private BigDecimal vatRate;

    @NotNull
    @NotBlank
    @NotEmpty
    private String barcode;

    private int stockQuantity;

    private int minStockQuantity;

    @NotNull
    @NotBlank
    @NotEmpty
    private String supplierUuid;

    public CarPart toCarPart() {
        CarPart carPart = new CarPart();
        carPart.setName(this.name);
        carPart.setDescription(this.description);
        carPart.setCostPrice(this.costPrice);
        carPart.setSellingPrice(this.sellingPrice);
        carPart.setVatRate(this.vatRate);
        carPart.setBarcode(this.barcode);
        carPart.setStockQuantity(this.stockQuantity);
        carPart.setMinStockQuantity(this.minStockQuantity);
        return carPart;
    }

}
