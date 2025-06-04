package com.example.crud_jwt.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.crud_jwt.model.User;
import com.example.crud_jwt.repository.IRole;
import com.example.crud_jwt.repository.IUser;
import com.example.crud_jwt.DTO.RoleDTO;
import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.model.PermissionRole;
import com.example.crud_jwt.model.Role;
import com.example.crud_jwt.repository.IPermissionRole;

@Service
public class RoleService {

    @Autowired
    private IRole repository;

    @Autowired
    private IUser userRepository;

    @Autowired
    private IPermissionRole permissionRoleRepository;

    // Listar todos
    public List<Role> findAll() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<Role> findById(int id) {
        return repository.findById(id);
    }

    // Eliminar
    public responseDTO delete(int id) {
        Optional<Role> optional = findById(id);
        if (!optional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "El registro no existe.");
        }
        repository.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Se eliminó correctamente.");
    }

    // Guardar o actualizar
    public responseDTO save(RoleDTO roleDTO) {
        if (roleDTO.getName() == null || roleDTO.getName().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre del rol es obligatorio.");
        }

        if (roleDTO.getName().length() > 50) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre no puede superar 50 caracteres.");
        }

        Role role = convertToModel(roleDTO);

        // Verificar si es actualización
        if (roleDTO.getId() > 0) {
            Optional<Role> existing = repository.findById(roleDTO.getId());
            if (!existing.isPresent()) {
                return new responseDTO(HttpStatus.NOT_FOUND.toString(), "No se encontró el rol para actualizar.");
            }
            Role existingRole = existing.get();
            existingRole.setName(role.getName());

            existingRole.setUsers(role.getUsers());
            existingRole.setPermissionRoles(role.getPermissionRoles());

            repository.save(existingRole);
            return new responseDTO(HttpStatus.OK.toString(), "Rol actualizado correctamente.");
        } else {
            // Guardar nuevo
            repository.save(role);
            return new responseDTO(HttpStatus.OK.toString(), "Rol registrado correctamente.");
        }
    }

    // Convertir de DTO a entidad
    public Role convertToModel(RoleDTO dto) {
        Role role = new Role();
        role.setId(dto.getId()); // si es para update
        role.setName(dto.getName());

        if (dto.getUserIds() != null && !dto.getUserIds().isEmpty()) {
            List<User> users = userRepository.findAllById(dto.getUserIds());
            role.setUsers(users);
        }

        if (dto.getPermissionRoleIds() != null && !dto.getPermissionRoleIds().isEmpty()) {
            List<PermissionRole> permissions = permissionRoleRepository.findAllById(dto.getPermissionRoleIds());
            role.setPermissionRoles(permissions);
        }

        return role;
    }

    // Convertir de entidad a DTO
    public RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());

        List<Integer> userIds = role.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        List<Integer> permissionRoleIds = role.getPermissionRoles().stream()
                .map(PermissionRole::getId)
                .collect(Collectors.toList());

        dto.setUserIds(userIds);
        dto.setPermissionRoleIds(permissionRoleIds);

        return dto;
    }
}
