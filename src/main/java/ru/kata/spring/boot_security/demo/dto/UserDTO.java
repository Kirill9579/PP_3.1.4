package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String password;
    private String email;
    private Set<RoleDTO> roles = new HashSet<>();
}
