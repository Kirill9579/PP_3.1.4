package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    void addUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    void removeUserById(Long id);

    void updateUser(User user, Long id);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
