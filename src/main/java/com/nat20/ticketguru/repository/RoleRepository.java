package com.nat20.ticketguru.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nat20.ticketguru.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByTitle(String title);

    @Query(value = "SELECT * FROM roles WHERE deleted_at IS NULL", nativeQuery = true)
    List<Role> findAllActive();

    @Query(value = "SELECT * FROM roles WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Role> findByIdActive(@Param("id") Long id);

    @Query(value = "SELECT * FROM roles WHERE title = :title AND deleted_at IS NULL", nativeQuery = true)
    Optional<Role> findByTitleActive(@Param("title") String title);

}
