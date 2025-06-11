package com.sys4business.sys4mech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    @Column(name = "uuid")
    private String uuid;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description")
    private String description;

    public Permission() {
        super();
    }

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
