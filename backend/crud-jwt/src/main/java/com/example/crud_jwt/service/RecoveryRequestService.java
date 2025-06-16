package com.example.crud_jwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.crud_jwt.repository.IRecoveryRequest;
import com.example.crud_jwt.model.RecoveryRequest;
import com.example.crud_jwt.DTO.RecoveryRequestDTO;
import com.example.crud_jwt.DTO.responseDTO;
import com.example.crud_jwt.model.User;
import com.example.crud_jwt.repository.IUser;

@Service
public class RecoveryRequestService {

    @Autowired
    private IRecoveryRequest repository;

    @Autowired
    private IUser userRepository;

    // Listar todos
    public List<RecoveryRequest> findAll() {
        return repository.findAll();
    }

    // Buscar por ID
    public Optional<RecoveryRequest> findById(int id) {
        return repository.findById(id);
    }

    // Eliminar
    public responseDTO delete(int id) {
        Optional<RecoveryRequest> optional = findById(id);
        if (!optional.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "El registro no existe.");
        }
        repository.deleteById(id);
        return new responseDTO(HttpStatus.OK.toString(), "Se eliminó correctamente.");
    }

    public responseDTO save(RecoveryRequestDTO dto) {
        // Validaciones básicas
        if (dto.getToken() == null || dto.getToken().trim().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El token es obligatorio.");
        }

        if (dto.getExpiresAt() == null) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "La fecha de expiración es obligatoria.");
        }

        if (dto.getUserId() == null) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El usuario asociado es obligatorio.");
        }

        // Buscar el usuario
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (!userOpt.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "Usuario no encontrado con id: " + dto.getUserId());
        }

        // Convertir DTO a entidad
        RecoveryRequest request = convertToModel(dto);
        request.setUser(userOpt.get());

        // ACTUALIZACIÓN
        if (dto.getId() > 0) {
            Optional<RecoveryRequest> existingOpt = repository.findById(dto.getId());
            if (!existingOpt.isPresent()) {
                return new responseDTO(HttpStatus.NOT_FOUND.toString(), "No se encontró la solicitud para actualizar.");
            }

            RecoveryRequest existing = existingOpt.get();
            existing.setToken(request.getToken());
            existing.setUsed(request.isUsed());
            existing.setExpiresAt(request.getExpiresAt());
            existing.setUser(request.getUser());

            repository.save(existing);
            return new responseDTO(HttpStatus.OK.toString(), "Solicitud actualizada correctamente.");
        }

        // CREACIÓN
        repository.save(request);
        return new responseDTO(HttpStatus.OK.toString(), "Solicitud registrada correctamente.");
    }

    public RecoveryRequest convertToModel(RecoveryRequestDTO dto) {
        User user = null;
        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUserId()));
        }

        return new RecoveryRequest(
                dto.getId(),
                dto.getToken(),
                dto.isUsed(),
                dto.getExpiresAt(),
                user);
    }

    public RecoveryRequestDTO convertToDTO(RecoveryRequest model) {
        return new RecoveryRequestDTO(
                model.getId(),
                model.getToken(),
                model.isUsed(),
                model.getExpiresAt(),
                model.getUser() != null ? model.getUser().getId() : null);
    }

}
