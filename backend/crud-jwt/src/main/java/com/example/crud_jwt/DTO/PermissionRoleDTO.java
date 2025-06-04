package com.example.crud_jwt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.crud_jwt.model.Page;
import com.example.crud_jwt.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRoleDTO {
    private int id;
    private Role role;
    private Page page;
    private boolean canRead;
    private boolean canWrite;
    private boolean canUpdate;
    private boolean canDelete;
}
