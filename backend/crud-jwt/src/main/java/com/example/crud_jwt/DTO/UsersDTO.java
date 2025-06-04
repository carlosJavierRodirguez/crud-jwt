package com.example.crud_jwt.DTO;

import com.example.crud_jwt.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private int id;
    private String name;
    private String lastName;
    private String email;
    private boolean status;
    private String password;
    private Role role;
}
