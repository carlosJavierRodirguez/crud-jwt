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

import com.example.crud_jwt.DTO.RoleDTO;

import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.service.RoleService;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    // guarda los datos
    @PostMapping("/")
    public ResponseEntity<Object> registerRole(@RequestBody RoleDTO roleDTO) {
        responseDTO message = roleService.save(roleDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // lista los datos de la tabla
    @GetMapping("/")
    public ResponseEntity<Object> getAllRole() {
        var message = roleService.findAll();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // borrar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable int id) {
        var message = roleService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
