package com.nat20.ticketguru.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "role_permissions")
public class RolePermission {
    @ManyToOne
    @JoinColumn(name = "roles")
    Role role;

    @ManyToOne
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
