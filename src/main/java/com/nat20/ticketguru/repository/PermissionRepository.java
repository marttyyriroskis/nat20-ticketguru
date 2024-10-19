package com.nat20.ticketguru.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nat20.ticketguru.domain.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Long> {

    Optional<Permission> findByTitle(String title);

    @Query(value = "SELECT * FROM permissions WHERE deleted_at IS NULL", nativeQuery = true)
    List<Permission> findAllActive();

    @Query(value = "SELECT * FROM permissions WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Permission> findByIdActive(@Param("id") Long id);

    @Query(value = "SELECT * FROM permissions WHERE title = :title AND deleted_at IS NULL", nativeQuery = true)
    Optional<Permission> findByTitleActive(@Param("title") String title);

}
