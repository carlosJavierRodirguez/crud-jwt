package com.example.crud_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud_jwt.DTO.PermissionRoleDTO;
import com.example.crud_jwt.DTO.RecoveryRequestDTO;
import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.service.PermissionRoleService;
import com.example.crud_jwt.service.RecoveryRequestService;

@RestController
@RequestMapping("/api/v1/permission-roles")
public class PermissionRoleController {
    @Autowired
    private PermissionRoleService permissionRoleService;

    // guarda los datos
    @PostMapping("/")
    public ResponseEntity<Object> registerPermissionRole(@RequestBody PermissionRoleDTO permissionRoleDTO) {
        responseDTO message = permissionRoleService.save(permissionRoleDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // lista los datos de la tabla
    @GetMapping("/")
    public ResponseEntity<Object> getAllPermissionRoles() {
        var message = permissionRoleService.findAll();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // borrar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePermissionRole(@PathVariable int id) {
        var message = permissionRoleService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
