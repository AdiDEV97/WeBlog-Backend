package com.security.blogs.Service;

import com.security.blogs.Model.Role;
import com.security.blogs.Payloads.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAllRoles();

    RoleDto getRoleById(int id);

    RoleDto newRole(RoleDto roleDto);

    RoleDto updateRole(Role role, int id);

    void deleteRole(int id);
}
