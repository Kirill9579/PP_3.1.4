package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AdminRESTController {

    private final UserService userService;
    private final Converter converter;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminRESTController(UserService userService, Converter converter, RoleRepository roleRepository) {
        this.userService = userService;
        this.converter = converter;
        this.roleRepository = roleRepository;
    }


    @DeleteMapping("/admin/{id}")
    public ResponseEntity<HttpStatus> removeUserById(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/admin/")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDTO userDTO) {

        userService.updateUser(converter.convertToUser(userDTO), userDTO.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/admin/")
    public ResponseEntity<UserDTO> saveNewUser(@RequestBody UserDTO userDTO) {
        User user = converter.convertToUser(userDTO);
        userService.addUser(user);
        userDTO = converter.convertToUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/admin/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            userDTOList.add(converter.convertToUserDTO(user));
        }

        return ResponseEntity.ok(userDTOList);
    }
}
