package com.company.dashboard.service;

import java.util.List;

import com.company.dashboard.model.EntityMaster;
import com.company.dashboard.response.ApiResponse;

public interface EntityMasterService {

    ApiResponse<List<EntityMaster>> getAllEntities();

    ApiResponse<EntityMaster> getEntityById(Integer id);

    ApiResponse<EntityMaster> createEntity(EntityMaster entity);

    ApiResponse<EntityMaster> patchEntity(Integer id, EntityMaster updates);

    ApiResponse<Void> deleteEntity(Integer id);
}