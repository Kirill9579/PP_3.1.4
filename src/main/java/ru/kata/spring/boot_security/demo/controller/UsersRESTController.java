package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class UsersRESTController {

    private final UserService userService;

    @Autowired
    public UsersRESTController(UserService userService, Converter converter) {
        this.userService = userService;
    }
    @GetMapping("/user/")
    public String getOneUser() {
        return "../js-view/AdminPanel";
    }

    @GetMapping("/api/auth/")
    @ResponseBody
    public UserDTO getAuthUsers(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
            return userService.findByEmail(user.getUsername());
    }
}
