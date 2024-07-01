package com.example.demo.services.role;

import com.example.demo.exceptions.DuplicateKeyException;
import com.example.demo.models.users.Role;
import com.example.demo.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role insertNewRole(String roleName, String description) throws DuplicateKeyException {
        Optional<Role> role = roleRepository.findByName(roleName);
        if(role.isPresent()){
            throw new DuplicateKeyException("This Role has already exits");
        }

        Role newRole = Role.builder()
                .name(roleName)
                .description(description)
                .build();

        return roleRepository.save(newRole);
    }
}
