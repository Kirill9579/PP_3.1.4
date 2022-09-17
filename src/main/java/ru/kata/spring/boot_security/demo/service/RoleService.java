package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;


public interface RoleService {

    void saveRole(Role role);
    List<RoleDTO> getAllRoles();
    Role findRoleByName(String name);
}
