package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    User add(User user);
    void delete(Long id);
    User update(Long id, User user);
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
}
