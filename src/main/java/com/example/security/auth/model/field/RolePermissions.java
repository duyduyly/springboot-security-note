package com.example.security.auth.model.field;

import com.example.security.auth.model.entity.Permission;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RolePermissions {
    private String roleName;
    private List<Permission> permissions;
}
