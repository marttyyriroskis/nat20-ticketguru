package com.nat20.ticketguru.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nat20.ticketguru.dto.RoleDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(String title) {
        this.title = title;
    }

    public Role(String title, Set<User> users) {
        this.title = title;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.deletedAt = null;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        this.permissions.remove(permission);
    }

    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", title=" + title + "]";
    }

    public RoleDTO toDTO() {
        return new RoleDTO(
            this.title,

            this.permissions.stream()
                .map(Permission::getId)
                .collect(Collectors.toSet()),

            this.users.stream()
                .map(User::getId)
                .collect(Collectors.toList()) 
        );
    }

}
