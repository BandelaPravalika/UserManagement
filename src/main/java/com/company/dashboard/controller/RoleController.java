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

import com.company.dashboard.model.Role;
import com.company.dashboard.response.ApiResponse;
import com.company.dashboard.service.RoleService;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ApiResponse<List<Role>> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public ApiResponse<Role> getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    @PostMapping
    public ApiResponse<Role> createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Role> patchRole(
            @PathVariable Integer id,
            @RequestBody Role updates) {
        return roleService.patchRole(id, updates);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRole(@PathVariable Integer id) {
        return roleService.deleteRole(id);
    }
}
