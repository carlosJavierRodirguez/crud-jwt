package com.example.crud_jwt.controller;

import java.util.List;
import java.util.Optional;

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

import com.example.crud_jwt.DTO.PageDTO;
import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.model.Page;
import com.example.crud_jwt.service.PageService;

@RestController
@RequestMapping("/api/v1/page")
public class PageController {
    @Autowired
    private PageService pageService;

    // Listar todos
    @GetMapping("/")
    public ResponseEntity<List<Page>> getAllPages() {
        List<Page> pages = pageService.findAll();
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Page> getPageById(@PathVariable int id) {
        Optional<Page> page = pageService.findById(id);
        if (page.isPresent()) {
            return new ResponseEntity<>(page.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<responseDTO> deletePage(@PathVariable int id) {
        responseDTO response = pageService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // Guardar o actualizar
    @PostMapping("/")
    public ResponseEntity<responseDTO> savePage(@RequestBody PageDTO dto) {
        responseDTO response = pageService.save(dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
