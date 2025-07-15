package com.sys4business.sys4mech.models.dtos;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import com.sys4business.sys4mech.models.CustomerCars;
import com.sys4business.sys4mech.services.CustomerService;

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

    @Autowired
    private CustomerService customerService;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 5, max = 100)
    private String make;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 5, max = 100)
    private String model;
    @NotNull
    @NotBlank
    @NotEmpty
    private Integer year;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 3, max = 30)
    private String color;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    private String registration_number;
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
        customerCar.setRegistrationNumber(this.registration_number);
        customerCar.setVin(this.vin);
        customerCar.setCustomer(customerService.findCustomerByUuid(customerUuid));
        return customerCar;
    }

}
