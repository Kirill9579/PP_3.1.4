package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
public class UsersController {

    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/registration")
    public String registration(ModelMap model) {
        model.addAttribute("users", new User());
        return "crud/registration";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/";
    }

    // --------------- Admin -------------
    @GetMapping("/admin/")
    public String getAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "crud/users";
    }
    @GetMapping("/admin/{id}")
    public String showUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "crud/oneUser";
    }
    @GetMapping("/admin/delete/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/";
    }
    @PostMapping("/admin/{id}")
    public String updateUserByAdmin(@ModelAttribute("user") User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin/";
    }
    @GetMapping("/admin/{id}/update")
    public String editUserByAdmin(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "crud/updateUser";
    }
    @GetMapping("/admin/new")
    public String newUserByAdmin(ModelMap model) {
        model.addAttribute("user", new User());
        return "crud/addUser";
    }

    @PostMapping("/admin/new")
    public String createUserByAdmin(@ModelAttribute("user") User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/admin/";
    }

    ///----------- User ---------
    @GetMapping("/user/")
    public String showOneUser(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

}
