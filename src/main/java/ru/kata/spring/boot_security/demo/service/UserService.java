package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO findByEmail(String email);

    UserDTO addUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO removeUserById(Long id);

    UserDTO updateUser(UserDTO userDTO, Long id);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
