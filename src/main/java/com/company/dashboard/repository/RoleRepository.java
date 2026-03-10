package com.company.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleNameIgnoreCase(String roleName);
    boolean existsByRoleNameIgnoreCase(String roleName);

    Optional<Role> findByRoleCodeIgnoreCase(String roleCode);
    boolean existsByRoleCodeIgnoreCase(String roleCode);
    Optional<Role> findByRoleName(String roleName);
}
