package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

//    @GetMapping("/admin/")
//    public String getAllUsers(ModelMap model) {
//        List<User> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "crud/users";
//    }
    @GetMapping("/admin/{id}")
    public String showOneUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "crud/oneUser";
    }
    @GetMapping("/admin/delete/{id}")
    public String removeUserById(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/";
    }
    @PostMapping("/admin/update/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/";
    }
    @GetMapping("/admin/update/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "crud/updateUser";
    }
    @GetMapping("/admin/new")
    public String createNewUser(ModelMap model) {
        Set<Role> listRoles = new HashSet<>(roleRepository.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", listRoles);
        return "crud/addUser";
    }

    @PostMapping("/admin/new")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/")
    public String showOneUser(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<User> users = userService.getAllUsers();
        model.addAttribute("user_service", userService);
        model.addAttribute("users", users);
        model.addAttribute("one_user", user);
        return "bootstrap/temp";
    }
}
