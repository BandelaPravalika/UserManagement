package com.company.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.dashboard.model.EntityMaster;
import com.company.dashboard.response.ApiResponse;
import com.company.dashboard.service.EntityMasterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/entities")

public class EntityMasterController {

    @Autowired
    private EntityMasterService entityMasterService;

    @GetMapping
    public ApiResponse<List<EntityMaster>> getAllEntities() {
        return entityMasterService.getAllEntities();
    }

    @GetMapping("/{id}")
    public ApiResponse<EntityMaster> getEntityById(@PathVariable Integer id) {
        return entityMasterService.getEntityById(id);
    }

    @PostMapping
    public ApiResponse<EntityMaster> createEntity(
            @Valid @RequestBody EntityMaster entity) {
        return entityMasterService.createEntity(entity);
    }

    @PatchMapping("/{id}")
    public ApiResponse<EntityMaster> updateEntity(
            @PathVariable Integer id,
            @RequestBody EntityMaster updates) {
        return entityMasterService.patchEntity(id, updates);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEntity(@PathVariable Integer id) {
        return entityMasterService.deleteEntity(id);
    }
}

