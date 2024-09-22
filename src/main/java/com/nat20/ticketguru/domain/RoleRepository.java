package com.nat20.ticketguru.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByTitle(String title);

}
