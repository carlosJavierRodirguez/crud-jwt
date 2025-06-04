package com.example.crud_jwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.crud_jwt.repository.IUser;
import com.example.crud_jwt.DTO.UsersDTO;
import com.example.crud_jwt.model.User;
import com.example.crud_jwt.DTO.responseDTO;

@Service
public class UserService {

    @Autowired
    private IUser repository;

    // listar todas las columnas
    public List<User> findAll() {
        return repository.findAll();
    }

    // lista segun el id
    public Optional<User> findById(int id) {
        return repository.findById(id);
    }

    // eliminar por id
    public responseDTO delete(int id) {
        if (!findById(id).isPresent()) {
            return new responseDTO(
                    HttpStatus.OK.toString(),
                    "El registro no existe.");
        }
        repository.deleteById(id);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Se eliminó correctamente.");
    }

    // guardar y actualizar
    public responseDTO save(UsersDTO usersDTO) {

        if (usersDTO.getName() == null || usersDTO.getName().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre no puede estar vacío.");
        }

        if (usersDTO.getName().length() > 100) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre no puede superar los 100 caracteres.");
        }

        if (usersDTO.getEmail() == null || usersDTO.getEmail().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El correo electrónico no puede estar vacío.");
        }

        if (usersDTO.getEmail().length() > 100) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(),
                    "El correo electrónico no puede superar los 100 caracteres.");
        }

        if (usersDTO.getPassword() == null || usersDTO.getPassword().isEmpty()) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "La contraseña no puede estar vacía.");
        }

        if (usersDTO.getPassword().length() > 100) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(),
                    "La contraseña no puede superar los 100 caracteres.");
        }

        if (usersDTO.getRole() == null) {
            return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El rol no puede ser nulo.");
        }

        // Convertimos el DTO a entidad
        User user = convertToModel(usersDTO);

        if (usersDTO.getId() > 0) {
            Optional<User> existingUserOptional = repository.findById(usersDTO.getId());

            if (!existingUserOptional.isPresent()) {
                return new responseDTO(HttpStatus.NOT_FOUND.toString(),
                        "El usuario con ID " + usersDTO.getId() + " no fue encontrado.");
            }

            // Actualizar datos
            User existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());
            existingUser.setStatus(user.isStatus());

            repository.save(existingUser);

            return new responseDTO(HttpStatus.OK.toString(), "El usuario se actualizó correctamente.");
        } else {
            // Guardar nuevo usuario
            repository.save(user);
            return new responseDTO(HttpStatus.OK.toString(), "El usuario se registró correctamente.");
        }
    }

    public User convertToModel(UsersDTO usersDTO) {
        return new User(
                usersDTO.getId(),
                usersDTO.getName(),
                usersDTO.getLastName(),
                usersDTO.getEmail(),
                true,
                usersDTO.getPassword(),
                usersDTO.getRole());
    }

    public UsersDTO convertToDTO(User user) {
        return new UsersDTO(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                true,
                user.getPassword(),
                user.getRole());
    }

}
