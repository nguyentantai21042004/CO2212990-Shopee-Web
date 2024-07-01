package com.example.demo.services.role;

import com.example.demo.exceptions.DuplicateKeyException;
import com.example.demo.models.users.Role;
import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();

    Role insertNewRole(String roleName, String description) throws DuplicateKeyException;
}
