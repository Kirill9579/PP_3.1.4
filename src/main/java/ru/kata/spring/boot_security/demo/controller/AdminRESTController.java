package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.List;

@Controller
public class AdminRESTController {

    private final UserService userService;

    @Autowired
    public AdminRESTController(UserService userService, Converter converter, RoleRepository roleRepository) {
        this.userService = userService;
    }

    @GetMapping("/admin/")
    public String adminPage() {
        return "../js-view/AdminPanel";
    }

    @DeleteMapping("/api/admin/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> removeUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.removeUserById(id));
    }

    @PatchMapping("/api/admin/")
    @ResponseBody
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userDTO, userDTO.getId()));
    }

    @PostMapping("/api/admin/")
    @ResponseBody
    public ResponseEntity<UserDTO> saveNewUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/api/admin/")
    @ResponseBody
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/api/admin/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
