package com.nat20.ticketguru.repository;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Long> {

}
