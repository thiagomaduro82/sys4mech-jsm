package com.sys4business.sys4mech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles_permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissions extends BaseEntity {
    
    @Column(name = "role_id")
    @NotNull
    private Long roleId;
    @Column(name = "permission_id")
    @NotNull
    private Long permissionId;

}

