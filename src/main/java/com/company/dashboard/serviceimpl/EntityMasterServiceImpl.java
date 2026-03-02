package com.company.dashboard.serviceimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dashboard.model.EntityMaster;
import com.company.dashboard.repository.EntityMasterRepository;
import com.company.dashboard.response.ApiResponse;
import com.company.dashboard.service.EntityMasterService;

@Service
public class EntityMasterServiceImpl implements EntityMasterService {

    private static final Logger log = LoggerFactory.getLogger(EntityMasterServiceImpl.class);

    @Autowired
    private EntityMasterRepository entityMasterRepository;

    @Override
    public ApiResponse<List<EntityMaster>> getAllEntities() {
        List<EntityMaster> list = entityMasterRepository.findAll();
        return list.isEmpty()
                ? ApiResponse.failure("No entities found")
                : ApiResponse.success("Entities retrieved successfully", list);
    }

    @Override
    public ApiResponse<EntityMaster> getEntityById(Integer id) {
        return entityMasterRepository.findById(id)
                .map(e -> ApiResponse.success("Entity found", e))
                .orElseGet(() -> ApiResponse.failure("Entity not found: " + id));
    }

    @Override
    @Transactional
    public ApiResponse<EntityMaster> createEntity(EntityMaster entity) {
        if (entity == null) {
            return ApiResponse.failure("Entity object cannot be null");
        }

        // Protect against client sending existing ID
        entity.setEntityId(null);

        String code = (entity.getEntityCode() != null) ? entity.getEntityCode().trim() : null;
        String name = (entity.getEntityName() != null) ? entity.getEntityName().trim() : null;

        if (code == null || code.isEmpty()) {
            return ApiResponse.failure("Entity code is mandatory");
        }
        if (name == null || name.isEmpty()) {
            return ApiResponse.failure("Entity name is mandatory");
        }

        // Apply cleaned values
        entity.setEntityCode(code);
        entity.setEntityName(name);

        if (entityMasterRepository.existsByEntityCodeIgnoreCase(code)) {
            return ApiResponse.failure("Entity code already exists: " + code);
        }

        if (entityMasterRepository.existsByEntityNameIgnoreCase(name)) {
            return ApiResponse.failure("Entity name already exists: " + name);
        }

        EntityMaster saved;
        try {
            saved = entityMasterRepository.save(entity);
        } catch (Exception e) {
            log.error("Failed to save entity: code={}, name={}", code, name, e);
            return ApiResponse.failure("Failed to create entity: " + e.getMessage());
        }

        log.info("Entity created: id={}, code={}, name={}",
                saved.getEntityId(), saved.getEntityCode(), saved.getEntityName());

        return ApiResponse.success("Entity created successfully", saved);
    }
    @Override
    @Transactional
    public ApiResponse<EntityMaster> patchEntity(Integer id, EntityMaster updates) {
        try {
            EntityMaster existing = entityMasterRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Entity not found: " + id));

            if (updates.getEntityCode() != null &&
                !updates.getEntityCode().equalsIgnoreCase(existing.getEntityCode())) {

                if (entityMasterRepository.existsByEntityCodeIgnoreCase(updates.getEntityCode())) {
                    return ApiResponse.failure("Entity code already exists: " + updates.getEntityCode());
                }
                existing.setEntityCode(updates.getEntityCode());
            }

            if (updates.getEntityName() != null &&
                !updates.getEntityName().equalsIgnoreCase(existing.getEntityName())) {

                if (entityMasterRepository.existsByEntityNameIgnoreCase(updates.getEntityName())) {
                    return ApiResponse.failure("Entity name already exists: " + updates.getEntityName());
                }
                existing.setEntityName(updates.getEntityName());
            }

            EntityMaster saved = entityMasterRepository.save(existing);
            return ApiResponse.success("Entity updated successfully", saved);

        } catch (Exception e) {
            return ApiResponse.failure("Failed to update entity: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteEntity(Integer id) {
        try {
            if (!entityMasterRepository.existsById(id)) {
                return ApiResponse.failure("Entity not found: " + id);
            }
            entityMasterRepository.deleteById(id);
            return ApiResponse.success("Entity deleted successfully", null);
        } catch (Exception e) {
            return ApiResponse.failure("Failed to delete entity: " + e.getMessage());
        }
    }
}