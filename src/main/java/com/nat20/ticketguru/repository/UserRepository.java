package com.nat20.ticketguru.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nat20.ticketguru.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE deleted_at IS NULL", nativeQuery = true)
    List<User> findAllActive();

    @Query(value = "SELECT * FROM users WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<User> findByIdActive(@Param("id") Long id);

    @Query(value = "SELECT * FROM users WHERE email = :email AND deleted_at IS NULL", nativeQuery = true)
    Optional<User> findByEmailActive(@Param("email") String email);

}
