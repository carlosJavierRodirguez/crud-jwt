package com.example.crud_jwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.crud_jwt.DTO.PermissionRoleDTO;
import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.model.PermissionRole;
import com.example.crud_jwt.model.RecoveryRequest;
import com.example.crud_jwt.repository.IPermissionRole;
import com.example.crud_jwt.repository.IRecoveryRequest;
import com.example.crud_jwt.repository.IRole;
import com.example.crud_jwt.model.Role;
import com.example.crud_jwt.repository.IPage;
import com.example.crud_jwt.model.Page;

@Service
public class PermissionRoleService {
    @Autowired
    private IPermissionRole repository;

    @Autowired
    private IRole roleRepository;

    @Autowired
    private IPage pageRepository;

    // Listar todos
    public List<PermissionRole> findAll() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<PermissionRole> findById(int id) {
        return repository.findById(id);
    }

    // Eliminar
    public responseDTO delete(int id) {
        Optional<PermissionRole> optional = findById(id);
        if (!optional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "El registro no existe.");
        }
        repository.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Se eliminó correctamente.");
    }

    public responseDTO save(PermissionRoleDTO dto) {
        // Validaciones básicas
        if (dto.getRoleId() <= 0) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El rol es obligatorio.");
        }

        if (dto.getPageId() <= 0) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "La página es obligatoria.");
        }

        // Buscar el rol
        Optional<Role> roleOpt = roleRepository.findById(dto.getRoleId());
        if (!roleOpt.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "Rol no encontrado con id: " + dto.getRoleId());
        }

        // Buscar la página
        Optional<Page> pageOpt = pageRepository.findById(dto.getPageId());
        if (!pageOpt.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "Página no encontrada con id: " + dto.getPageId());
        }

        // Convertir DTO a entidad
        PermissionRole permissionRole = convertToModel(dto);

        // ACTUALIZACIÓN
        if (dto.getId() > 0) {
            Optional<PermissionRole> existingOpt = repository.findById(dto.getId());
            if (!existingOpt.isPresent()) {
                return new responseDTO(HttpStatus.NOT_FOUND.toString(), "No se encontró el permiso para actualizar.");
            }

            PermissionRole existing = existingOpt.get();
            existing.setRole(roleOpt.get());
            existing.setPage(pageOpt.get());
            existing.setCanRead(dto.isCanRead());
            existing.setCanWrite(dto.isCanWrite());
            existing.setCanUpdate(dto.isCanUpdate());
            existing.setCanDelete(dto.isCanDelete());

            repository.save(existing);
            return new responseDTO(HttpStatus.OK.toString(), "Permiso actualizado correctamente.");
        }

        // CREACIÓN
        permissionRole.setRole(roleOpt.get());
        permissionRole.setPage(pageOpt.get());
        repository.save(permissionRole);
        return new responseDTO(HttpStatus.OK.toString(), "Permiso registrado correctamente.");
    }

    public PermissionRole convertToModel(PermissionRoleDTO dto) {
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + dto.getRoleId()));

        Page page = pageRepository.findById(dto.getPageId())
                .orElseThrow(() -> new RuntimeException("Página no encontrada con id: " + dto.getPageId()));

        return new PermissionRole(
                dto.getId(),
                role,
                page,
                dto.isCanRead(),
                dto.isCanWrite(),
                dto.isCanUpdate(),
                dto.isCanDelete());
    }

    public PermissionRoleDTO convertToDTO(PermissionRole model) {
        return new PermissionRoleDTO(
                model.getId(),
                model.getRole() != null ? model.getRole().getId() : 0,
                model.getPage() != null ? model.getPage().getId() : 0,
                model.isCanRead(),
                model.isCanWrite(),
                model.isCanUpdate(),
                model.isCanDelete());
    }

}
