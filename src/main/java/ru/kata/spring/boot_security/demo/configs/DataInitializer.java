package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");
        roleRepository.findByName(admin.getName())
                .orElseGet(() -> roleRepository.save(admin));
        roleRepository.findByName(user.getName())
                .orElseGet(() -> roleRepository.save(user));
        User defaultAdmin = new User("admin", passwordEncoder.encode("admin"), "admin", "admin", 69, new HashSet<Role>());
        User defaultUser = new User("user", passwordEncoder.encode("user"), "user", "user", 96, new HashSet<Role>());
        if (userRepository.findByUsername(defaultAdmin.getUsername()).isEmpty()) {
            defaultAdmin.setRole(admin);
            defaultAdmin.setRole(user);
            userRepository.save(defaultAdmin);
        }
        if (userRepository.findByUsername(defaultUser.getUsername()).isEmpty()) {
            defaultUser.setRole(user);
            userRepository.save(defaultUser);
        }
    }
}
