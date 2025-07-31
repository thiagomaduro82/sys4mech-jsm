package com.sys4business.sys4mech.models.dtos;

import org.hibernate.validator.constraints.Length;

import com.sys4business.sys4mech.models.Supplier;

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
public class SupplierDTO {
    
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 1, max = 100)
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 1, max = 100)
    private String contactName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 20)
    private String phone;

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

    public Supplier toSupplier() {
        return new Supplier(null, name, contactName, email, phone, addressLine1, addressLine2, city, county, postalCode, country);
    }

}
