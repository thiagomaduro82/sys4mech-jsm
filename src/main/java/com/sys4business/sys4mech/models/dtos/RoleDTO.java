package com.sys4business.sys4mech.models.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import com.sys4business.sys4mech.models.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoleDTO {
    
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 3, max = 60, message = "Name must be between 3 and 60 characters")
    @UniqueElements(message = "Name must be unique")
    private String name;

    public RoleDTO() {
        super();
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;   
    }

    public Role toRole() {
        Role role = new Role();
        role.setName(this.name);
        return role;
    }

}
