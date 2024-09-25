package com.nat20.ticketguru.repository;

import org.springframework.data.repository.CrudRepository;

import com.nat20.ticketguru.domain.RolePermission;
import com.nat20.ticketguru.domain.RolePermissionKey;

public interface RolePermissionRepository extends CrudRepository<RolePermission, RolePermissionKey> {

}
