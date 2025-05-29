package com.sys4business.sys4mech.models.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoleDTO {
    
    @NotNull(message = "UUID cannot be null")
    @NotBlank(message = "UUID cannot be blank")
    @Length(min = 32, max = 32, message = "UUID must be 32 characters")
    private String uuid;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Length(min = 3, max = 60, message = "Name must be between 3 and 60 characters")
    private String name;

    public RoleDTO() {
        super();
    }

    public RoleDTO(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;   
    }
}
