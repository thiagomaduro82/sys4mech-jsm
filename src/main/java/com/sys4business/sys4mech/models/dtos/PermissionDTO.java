package com.sys4business.sys4mech.models.dtos;

import org.hibernate.validator.constraints.Length;

import com.sys4business.sys4mech.models.Permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PermissionDTO {
    
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 3, max = 60, message = "Name must be between 3 and 60 characters")
    private String name;
    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    @Length(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    private String description;

    public PermissionDTO() {
        super();
    }

    public PermissionDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Permission toPermission() {
        Permission permission = new Permission();
        permission.setName(this.name);
        permission.setDescription(this.description);
        return permission;
    }
}
