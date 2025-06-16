package com.example.crud_jwt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRoleDTO {

    private int id;
    private int roleId;
    private int pageId;
    private boolean canRead;
    private boolean canWrite;
    private boolean canUpdate;
    private boolean canDelete;

}
