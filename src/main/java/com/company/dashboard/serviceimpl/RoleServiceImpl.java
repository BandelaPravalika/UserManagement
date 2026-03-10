package com.company.dashboard.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.dashboard.model.Role;
import com.company.dashboard.repository.RoleRepository;
import com.company.dashboard.response.ApiResponse;
import com.company.dashboard.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ApiResponse<List<Role>> getAllRoles() {
        List<Role> list = roleRepository.findAll();
        return list.isEmpty()
                ? ApiResponse.failure("No roles found")
                : ApiResponse.success("Roles retrieved successfully", list);
    }

    @Override
    public ApiResponse<Role> getRoleById(Integer id) {
        return roleRepository.findById(id)
                .map(r -> ApiResponse.success("Role found", r))
                .orElseGet(() -> ApiResponse.failure("Role not found: " + id));
    }

    @Override
    public ApiResponse<Role> createRole(Role role) {
        try {
            if (role.getRoleCode() == null || role.getRoleCode().isBlank()) {
                return ApiResponse.failure("Role code is mandatory");
            }

            if (role.getRoleName() == null || role.getRoleName().isBlank()) {
                return ApiResponse.failure("Role name is mandatory");
            }

            if (roleRepository.existsByRoleCodeIgnoreCase(role.getRoleCode())) {
                return ApiResponse.failure("Role code already exists: " + role.getRoleCode());
            }

            if (roleRepository.existsByRoleNameIgnoreCase(role.getRoleName())) {
                return ApiResponse.failure("Role name already exists: " + role.getRoleName());
            }

            Role saved = roleRepository.save(role);
            return ApiResponse.success("Role created successfully", saved);

        } catch (Exception e) {
            return ApiResponse.failure("Failed to create role: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Role> patchRole(Integer id, Role updates) {
        try {
            Role existing = roleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + id));

            if (updates.getRoleCode() != null &&
                !updates.getRoleCode().equalsIgnoreCase(existing.getRoleCode())) {

                if (roleRepository.existsByRoleCodeIgnoreCase(updates.getRoleCode())) {
                    return ApiResponse.failure("Role code already exists: " + updates.getRoleCode());
                }
                existing.setRoleCode(updates.getRoleCode());
            }

            if (updates.getRoleName() != null &&
                !updates.getRoleName().equalsIgnoreCase(existing.getRoleName())) {

                if (roleRepository.existsByRoleNameIgnoreCase(updates.getRoleName())) {
                    return ApiResponse.failure("Role name already exists: " + updates.getRoleName());
                }
                existing.setRoleName(updates.getRoleName());
            }

            Role saved = roleRepository.save(existing);
            return ApiResponse.success("Role updated successfully", saved);

        } catch (Exception e) {
            return ApiResponse.failure("Failed to update role: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Void> deleteRole(Integer id) {
        try {
            if (!roleRepository.existsById(id)) {
                return ApiResponse.failure("Role not found: " + id);
            }
            roleRepository.deleteById(id);
            return ApiResponse.success("Role deleted successfully", null);

        } catch (Exception e) {
            return ApiResponse.failure("Failed to delete role: " + e.getMessage());
        }
    }
}
