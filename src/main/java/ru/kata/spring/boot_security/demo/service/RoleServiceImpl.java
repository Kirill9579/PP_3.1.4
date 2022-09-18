package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;
    private Converter converter;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, Converter converter) {
        this.roleRepository = roleRepository;
        this.converter = converter;
    }

    @Override
    public void saveRole(Role role) {
        if (roleRepository.findRoleByName(role.getName()) == null) {
            roleRepository.save(role);
        }
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<RoleDTO> roles = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            roles.add(converter.convertToRoleDTO(role));
        }
        return roles;
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
