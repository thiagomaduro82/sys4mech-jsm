package com.sys4business.sys4mech.models.dtos;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.sys4business.sys4mech.models.Service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;
    private BigDecimal amount;
    private BigDecimal vatRate;
    private Boolean electronicDiagnosis;

    public Service toService() {
        Service service = new Service();
        service.setName(this.name);
        service.setAmount(this.amount);
        service.setVatRate(this.vatRate);
        service.setElectronicDiagnosis(this.electronicDiagnosis);
        return service;
    }

}
