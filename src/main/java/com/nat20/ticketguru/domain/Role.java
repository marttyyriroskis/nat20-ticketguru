package com.nat20.ticketguru.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.nat20.ticketguru.dto.RoleDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Entity class for Role
 * 
 * @OneToMany relationship to User
 * @method delete()
 * @method isDeleted()
 * @method addPermissions()
 * @method removePermissions()
 * @method hasPermission()
 * @method toDTO()
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 50)
    @Column(unique = true)
    private String title;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
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

    public void delete() {
        this.deletedAt = LocalDateTime.now();
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

    public void addPermissions(Permission... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
    }

    public void removePermissions(Permission... permissions) {
        this.permissions.removeAll(Arrays.asList(permissions));
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
                this.id,
                this.title,
                this.permissions == null
                        ? Collections.emptyList()
                        : this.permissions.stream()
                                .map(Permission::toString)
                                .collect(Collectors.toList()),
                this.users == null
                        ? Collections.emptyList()
                        : this.users.stream()
                                .map(User::getId)
                                .collect(Collectors.toList())
        );
    }

}
