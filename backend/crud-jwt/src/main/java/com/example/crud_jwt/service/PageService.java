package com.example.crud_jwt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.crud_jwt.DTO.PageDTO;
import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.model.Page;
import com.example.crud_jwt.model.PermissionRole;
import com.example.crud_jwt.repository.IPage;
import com.example.crud_jwt.repository.IPermissionRole;

@Service
public class PageService {
    @Autowired
    private IPage repository;

    @Autowired
    private IPermissionRole permissionRoleRepository;

    // Listar todos
    public List<Page> findAll() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<Page> findById(int id) {
        return repository.findById(id);
    }

    // Eliminar
    public responseDTO delete(int id) {
        Optional<Page> optional = findById(id);
        if (!optional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "El registro no existe.");
        }
        repository.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Se eliminó correctamente.");
    }

    public responseDTO save(PageDTO dto) {

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.name(), "El nombre de la página es obligatorio.");

        }

        if (dto.getName().length() > 100) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre no puede superar 100 caracteres.");
        }

        // Convertir DTO a modelo
        Page page = convertToModel(dto);

        // Si es actualización
        if (dto.getId() > 0) {
            Optional<Page> existingOpt = repository.findById(dto.getId());
            if (!existingOpt.isPresent()) {
               return new responseDTO(HttpStatus.OK.name(), "Página actualizada correctamente.");

            }

            Page existing = existingOpt.get();
            existing.setName(page.getName());
            existing.setPath(page.getPath());
            existing.setDescription(page.getDescription());
            existing.setPermissionRoles(page.getPermissionRoles());

            repository.save(existing);
            return new responseDTO(HttpStatus.OK.toString(), "Página actualizada correctamente.");
        }

        // Si es nuevo registro
        repository.save(page);
        return new responseDTO(HttpStatus.OK.toString(), "Página registrada correctamente.");
    }

    public Page convertToModel(PageDTO dto) {
        List<PermissionRole> permissionRoles = new ArrayList<>();

        if (dto.getPermissionRoleIds() != null) {
            permissionRoles = permissionRoleRepository.findAllById(dto.getPermissionRoleIds());
        }

        Page page = new Page();
        page.setId(dto.getId());
        page.setName(dto.getName());
        page.setPath(dto.getPath());
        page.setDescription(dto.getDescription());
        page.setPermissionRoles(permissionRoles);

        return page;
    }

    public PageDTO convertToDTO(Page model) {
        List<Integer> permissionRoleIds = new ArrayList<>();

        if (model.getPermissionRoles() != null) {
            permissionRoleIds = model.getPermissionRoles()
                    .stream()
                    .map(PermissionRole::getId)
                    .collect(Collectors.toList());
        }

        return new PageDTO(
                model.getId(),
                model.getName(),
                model.getPath(),
                model.getDescription(),
                permissionRoleIds);
    }

}
