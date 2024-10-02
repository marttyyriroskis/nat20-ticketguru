package com.nat20.ticketguru.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RolePermissionKey implements Serializable {

    @Column(name = "role_id")
    Long roleId;

    @Column(name = "permission_id")
    Long permissionId;

    public RolePermissionKey() {
    }

    public RolePermissionKey(Long permissionId, Long roleId) {
        this.permissionId = permissionId;
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.roleId);
        hash = 73 * hash + Objects.hashCode(this.permissionId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RolePermissionKey other = (RolePermissionKey) obj;
        if (!Objects.equals(this.roleId, other.roleId)) {
            return false;
        }
        return Objects.equals(this.permissionId, other.permissionId);
    }

}
