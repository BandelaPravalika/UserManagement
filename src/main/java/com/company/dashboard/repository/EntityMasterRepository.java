package com.company.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.dashboard.model.EntityMaster;

public interface EntityMasterRepository extends JpaRepository<EntityMaster, Integer> {

    Optional<EntityMaster> findByEntityNameIgnoreCase(String entityName);
    boolean existsByEntityNameIgnoreCase(String entityName);

    Optional<EntityMaster> findByEntityCodeIgnoreCase(String entityCode);
    boolean existsByEntityCodeIgnoreCase(String entityCode);
    Optional<EntityMaster> findByEntityName(String entityName);
}
