package com.example.crud_jwt.DTO;

import java.util.List;

import com.example.crud_jwt.model.PermissionRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private int id;
    private String name;
    private String path;
    private String description;
    private List<Integer> permissionRoleIds;
}
