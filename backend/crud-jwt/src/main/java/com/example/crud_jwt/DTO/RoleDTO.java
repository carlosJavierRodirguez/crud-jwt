package com.example.crud_jwt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.crud_jwt.model.PermissionRole;
import com.example.crud_jwt.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private int id;
    private String name;
    private String description;
    private List<User> users;
    private List<PermissionRole> permissionRoles;
}
