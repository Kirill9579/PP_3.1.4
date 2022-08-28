package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UsersRESTController {

    private final UserService userService;
    private final Converter converter;

    @Autowired
    public UsersRESTController(UserService userService, Converter converter) {
        this.userService = userService;
        this.converter = converter;
    }
    @GetMapping("/auth/")
    public UserDTO getAuthUsers(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
            User myUser = userService.findByEmail(user.getUsername());

        return converter.convertToUserDTO(myUser);
    }
}
