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

import com.example.crud_jwt.DTO.UsersDTO;
import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    // guarda los datos
    @PostMapping("/")
    public ResponseEntity<Object> registerPatient(@RequestBody UsersDTO usersDTO) {
        responseDTO message = userService.save(usersDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // lista los datos de la tabla
    @GetMapping("/")
    public ResponseEntity<Object> getAllPatient() {
        var message = userService.findAll();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // borrar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        var message = userService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
