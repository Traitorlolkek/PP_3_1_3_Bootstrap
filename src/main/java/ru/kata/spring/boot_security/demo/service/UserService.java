package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserService {

    User findByUsername(String username);

    List<User> getAllUsers();

    boolean createUser(@ModelAttribute User user);

    User getEditUserPage(@PathVariable Long id);

    boolean editUser(@ModelAttribute("user") User user);

    boolean deleteUser(@PathVariable Long id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles);
}
