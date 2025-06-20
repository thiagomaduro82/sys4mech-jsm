package com.sys4business.sys4mech.models.dtos;

import com.sys4business.sys4mech.models.RolePermissions;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RolePermissionsDTO {

    @NotNull
    private Long roleId;

    @NotNull
    private Long permissionId;

    public RolePermissions toRolePermissions() {
        RolePermissions rolePermissions = new RolePermissions();
        rolePermissions.setRoleId(this.roleId);
        rolePermissions.setPermissionId(this.permissionId);
        return rolePermissions;
    }
    
}
