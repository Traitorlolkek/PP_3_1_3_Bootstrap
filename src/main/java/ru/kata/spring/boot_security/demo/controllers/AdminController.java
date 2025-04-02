package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImp;
import ru.kata.spring.boot_security.demo.services.UserServiceImp;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userService;
    private final RoleServiceImp roleService;

    @Autowired
    public AdminController(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Model model, Authentication authentication) {
        List<User> users = userService.findAll();  // Получаем всех пользователей
        List<Role> allRoles = roleService.findAll();  // Получаем все роли
        model.addAttribute("users", users);  // Добавляем пользователей в модель
        model.addAttribute("newUser", new User());  // Добавляем объект newUser для привязки к форме
        model.addAttribute("allRoles", allRoles);// Добавляем роли в модель
        model.addAttribute("currentUser", userService.findByUsername(authentication.getName()));
        return "admin/admin";  // Возвращаем представление для страницы администратора
    }

    @PostMapping
    public String addUser(@ModelAttribute("newUser") @Valid User newUser,
                          BindingResult result,
                          Model model,
                          Authentication authentication) {  // Добавляем authentication

        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("allRoles", roleService.findAll());
            model.addAttribute("currentUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("showNewUserForm", true);
            return "admin/admin";
        }

        userService.add(newUser);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult result,
                             Model model,
                             Authentication authentication) {

        if (result.hasErrors()) {
            // Добавляем все необходимые атрибуты для отображения страницы
            model.addAttribute("users", userService.findAll());
            model.addAttribute("allRoles", roleService.findAll());
            model.addAttribute("currentUser", userService.findByUsername(authentication.getName()));
            model.addAttribute("showEditModal", true); // Флаг для открытия модального окна
            model.addAttribute("editUserErrors", result.getAllErrors()); // Передаем ошибки

            // Сохраняем данные редактируемого пользователя
            model.addAttribute("editUser", user);

            return "admin/admin";
        }

        userService.update(user.getId(), user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

//    @GetMapping("/new")
//    public String add(Model model){
//        model.addAttribute("roles", roleService.findAll());
//        model.addAttribute("user", new User());
//        return "admin/new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("roles", roleService.findAll());
//            return "admin/new";
//        }
//        userService.add(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("roles", roleService.findAll());
//        model.addAttribute("user", userService.findById(id));
//        return "admin/edit";
//    }
//
//    @PostMapping("/{id}")
//    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id, Model model){
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("roles", roleService.findAll());
//            return "admin/edit";
//        }
//        userService.update(id, user);
//        return "redirect:/admin";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") Long id){
//        userService.delete(id);
//        return "redirect:/admin";
//    }
}
