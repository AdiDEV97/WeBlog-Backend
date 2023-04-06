package com.security.blogs.Service.Impl;

import com.security.blogs.Dao.RoleRepo;
import com.security.blogs.Model.Role;
import com.security.blogs.Payloads.RoleDto;
import com.security.blogs.Service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RoleDto> getAllRoles() {

        List<Role> allRoles = this.roleRepo.findAll();
        List<RoleDto> allRolesDto = allRoles.stream().map((role) -> this.modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
        return allRolesDto;
    }

    @Override
    public RoleDto getRoleById(int id) {

        Role roleById = this.roleRepo.findById(id).orElseThrow(() -> new RuntimeException());

        return this.modelMapper.map(roleById, RoleDto.class);
    }

    @Override
    public RoleDto newRole(RoleDto roleDto) {

        Role role = this.modelMapper.map(roleDto, Role.class);

        Role newRole = this.roleRepo.save(role);
        return this.modelMapper.map(newRole, RoleDto.class);
    }

    @Override
    public RoleDto updateRole(Role role, int id) {
        return null;
    }

    @Override
    public void deleteRole(int id) {

    }
}
