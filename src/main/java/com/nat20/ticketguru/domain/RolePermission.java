package com.nat20.ticketguru.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_permissions")
public class RolePermission {

    @EmbeddedId
    RolePermissionKey id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "roles")
    Role role;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permissions")
    Permission permission;

    public RolePermission() {
    }

    public RolePermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "RolePermission [role=" + role + ", permission=" + permission + "]";
    }

}
