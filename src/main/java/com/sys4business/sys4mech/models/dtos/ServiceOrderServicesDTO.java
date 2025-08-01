package com.sys4business.sys4mech.models.dtos;

import java.math.BigDecimal;

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
public class ServiceOrderServicesDTO {
    
    @NotNull
    @NotBlank
    @NotEmpty
    private String serviceOrderUuid;
    @NotNull
    @NotBlank
    @NotEmpty
    private String ServiceUuid;
    private Integer quantity;
    private BigDecimal amount;

}
