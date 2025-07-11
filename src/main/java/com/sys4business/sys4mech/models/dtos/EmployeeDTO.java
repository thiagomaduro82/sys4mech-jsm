package com.sys4business.sys4mech.models.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.sys4business.sys4mech.models.Employee;

import jakarta.validation.constraints.Email;
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
public class EmployeeDTO {
    
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 5, max = 60)
    private String name;
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 5, max = 100)
    private String addressLine1;
    @Length(max = 100)
    private String addressLine2;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 3, max = 50)
    private String city;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 3, max = 50)
    private String county;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    private String postalCode;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 3, max = 50)
    private String country;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    private String phone;

    public Employee toEmployee() {
        return new Employee(null, name, email, addressLine1, addressLine2, city, county, postalCode, country, dateOfBirth, phone);
    }
}
