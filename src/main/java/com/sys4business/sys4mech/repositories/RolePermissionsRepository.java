package com.sys4business.sys4mech.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys4business.sys4mech.models.RolePermissions;

@Repository
public interface RolePermissionsRepository extends JpaRepository<RolePermissions, Long> {

    Optional<RolePermissions> findByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
