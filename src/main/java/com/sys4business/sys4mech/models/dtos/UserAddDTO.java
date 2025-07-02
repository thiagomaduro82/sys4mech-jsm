package com.sys4business.sys4mech.models.dtos;

import org.hibernate.validator.constraints.Length;

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
public class UserAddDTO {
    
    @NotBlank(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    @Length(min = 3, max = 60, message = "Name must be between 3 and 60 characters")
    private String name;
    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Email is required")
    @NotEmpty(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    private String email;
    @NotBlank(message = "Password is required")
    @NotEmpty(message = "Password cannot be empty")
    @NotNull(message = "Password cannot be null")
    @Length(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
    @NotBlank(message = "Role UUID is required")
    @NotEmpty(message = "Role UUID cannot be empty")
    @NotNull(message = "Role UUID cannot be null")
    @Length(min = 32, max = 32, message = "Role UUID must be 32 characters long")
    private String roleUuid;

}
