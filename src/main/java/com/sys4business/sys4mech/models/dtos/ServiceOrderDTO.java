package com.sys4business.sys4mech.models.dtos;

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
public class ServiceOrderDTO {

    @NotNull
    @NotBlank
    @NotEmpty
    private String customerUuid;
    @NotNull
    @NotBlank
    @NotEmpty
    private String customerCarUuid;
    @NotNull
    @NotBlank
    @NotEmpty
    private String employeeUuid;
    @NotNull
    @NotBlank
    @NotEmpty
    private String status;
    @NotNull
    @NotBlank
    @NotEmpty
    private String workRequired;

    private String observations;

    @Override
    public String toString() {
        return "CustomerUUID: " + this.customerUuid + "\n" +
        "CustomerCarUUID: " + this.customerCarUuid + "\n" +
        "employeeUUID: " + this.employeeUuid + "\n" + 
        "Status: " + this.status + "\n" + 
        "workRequired: " + this.workRequired + "\n" + 
        "observations: " + this.observations + "\n";
    }

}
