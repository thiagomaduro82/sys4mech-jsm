package com.sys4business.sys4mech.models.dtos;

import org.hibernate.validator.constraints.Length;

import com.sys4business.sys4mech.models.CustomerCars;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerCarsDTO {

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 100)
    private String make;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 100)
    private String model;
    private Integer year;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 30)
    private String color;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    private String registrationNumber;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    private String vin;
    @NotNull
    @NotBlank
    @NotEmpty
    private String customerUuid;

    public CustomerCars toCustomerCars() {
        CustomerCars customerCar = new CustomerCars();
        customerCar.setMake(this.make);
        customerCar.setModel(this.model);
        customerCar.setYear(this.year);
        customerCar.setColor(this.color);
        customerCar.setRegistrationNumber(this.registrationNumber);
        customerCar.setVin(this.vin);
        return customerCar;
    }

}
