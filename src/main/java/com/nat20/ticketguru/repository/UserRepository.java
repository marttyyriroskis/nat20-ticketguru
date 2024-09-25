package com.nat20.ticketguru.repository;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
