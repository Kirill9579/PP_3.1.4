package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.Converter;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class InitUser {
    private final UserService userService;
    private final RoleService roleService;
    private final Converter converter;

    @Autowired
    public InitUser(UserService userService, RoleService roleService, Converter converter) {
        this.userService = userService;
        this.roleService = roleService;
        this.converter = converter;
    }
    @PostConstruct
    private void createAdminAndUser() {

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

        User admin = new User();
        admin.setFirstName("bob");
        admin.setLastName("fury");
        admin.setAge(52);
        admin.setPassword("admin");
        admin.setEmail("admin@mail.ru");
        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(roleUser);
        userService.addUser(converter.convertToUserDTO(admin));

        User user = new User();
        user.setFirstName("James");
        user.setLastName("Bond");
        user.setAge(78);
        user.setPassword("user");
        user.setEmail("user@mail.ru");
        user.getRoles().add(roleUser);
        userService.addUser(converter.convertToUserDTO(user));
    }
}
