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

import com.example.crud_jwt.DTO.RecoveryRequestDTO;
import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.service.RecoveryRequestService;

@RestController
@RequestMapping("/api/v1/recovery-requests")
public class RecoveryRequestController {
    @Autowired
    private RecoveryRequestService recoveryRequestService;

    // guarda los datos
    @PostMapping("/")
    public ResponseEntity<Object> registerRecoveryRequest(@RequestBody RecoveryRequestDTO recoveryRequestDTO) {
        responseDTO message = recoveryRequestService.save(recoveryRequestDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // lista los datos de la tabla
    @GetMapping("/")
    public ResponseEntity<Object> getAllRecoveryRequests() {
        var message = recoveryRequestService.findAll();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // borrar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecoveryRequest(@PathVariable int id) {
        var message = recoveryRequestService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}