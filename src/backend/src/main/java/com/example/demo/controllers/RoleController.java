package com.example.demo.controllers;

import com.example.demo.dtos.RoleDTO;
import com.example.demo.models.users.Role;
import com.example.demo.responses.ResponseObject;
import com.example.demo.services.role.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> getAllRole(){
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .data(roles)
                        .message("Get all roles successfully")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createRole(
            @Valid @RequestBody RoleDTO roleDTO,
            BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(ResponseObject.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .data(null)
                        .message(String.valueOf(errorMessages))
                        .build());
            }

            Role newRole = roleService.insertNewRole(roleDTO.getName(), roleDTO.getDescription());
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .data(newRole)
                            .message("Create role successfully")
                            .status(HttpStatus.OK)
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(e.getMessage())
                    .build());
        }
    }
}
