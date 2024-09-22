package com.nat20.ticketguru.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    @JsonIgnore
    private List<AppUser> appUsers;

    public Role() {
    }

    public Role(String title) {
        this.title = title;
    }

    public Role(String title, List<AppUser> appUsers) {
        this.title = title;
        this.appUsers = appUsers;
    }

    public long getId() {
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

    @Override
    public String toString() {
        return "Role [id=" + id + ", title=" + title + "]";
    }

    public List<AppUser> getUsers() {
        return appUsers;
    }

    public void setUsers(List<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

}
