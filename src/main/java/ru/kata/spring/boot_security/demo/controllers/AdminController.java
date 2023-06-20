package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String allUsers(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("principal", principal);
        model.addAttribute("users", userServiceImpl.allUsers());
        model.addAttribute("user", new User());
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);
        return "admin/users";
    }

    @GetMapping("/*/{id}")
    @ResponseBody
    public User getUserForModal(@PathVariable("id") Long id) {
        return userServiceImpl.findUserById(id);
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("principal", principal);
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.getRolesList());
        return "admin/newUser";
    }

    @PostMapping("/newUser")
    public String addUser(Model model, @ModelAttribute("user")  User user) {
        model.addAttribute("users", userServiceImpl.allUsers());
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);

        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }


    @PutMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user")  User user,  Model model) {
        List<Role> allRoles = roleService.getRolesList();
        model.addAttribute("allRoles", allRoles);

        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin/";
    }


}



