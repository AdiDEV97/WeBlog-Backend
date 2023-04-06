package com.security.blogs.Controller;

import com.security.blogs.Payloads.RoleDto;
import com.security.blogs.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleServ;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllRoles")
    public ResponseEntity<List<RoleDto>> allRoles() {
        List<RoleDto> allRoles = this.roleServ.getAllRoles();

        if(allRoles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            return ResponseEntity.of(Optional.of(allRoles));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roleId/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable int id) {
        RoleDto roleById = this.roleServ.getRoleById(id);

        if(roleById == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.of(Optional.of(roleById));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/newRole")
    public ResponseEntity<RoleDto> addNewRole(@RequestBody RoleDto roleDto) {
        try{
            RoleDto newRole = this.roleServ.newRole(roleDto);
            return ResponseEntity.of(Optional.of(newRole));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
