package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


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
        if (user == null) {
            UserDTO nullUser = new UserDTO();
            nullUser.setEmail("unknow@mail.ru");
            return nullUser;
        } else {
            User myUser = userService.findByEmail(user.getUsername());
            return converter.convertToUserDTO(myUser);
        }
    }
}
