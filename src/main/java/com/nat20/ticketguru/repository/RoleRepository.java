package com.nat20.ticketguru.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByTitle(String title);

}
