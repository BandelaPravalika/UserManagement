package com.company.dashboard.service;

import java.util.List;

import com.company.dashboard.model.Role;
import com.company.dashboard.response.ApiResponse;

public interface RoleService {

    ApiResponse<List<Role>> getAllRoles();

    ApiResponse<Role> getRoleById(Integer id);

    ApiResponse<Role> createRole(Role role);

    ApiResponse<Role> patchRole(Integer id, Role updates);

    ApiResponse<Void> deleteRole(Integer id);
}
