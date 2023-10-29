package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
//    todo здесь тоже исправить импорты
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", unique = true)
    @NotNull
    private String            roleName;
/*

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
*/

    public Role() {

    }
    public Role(String roleName) {
        this.roleName = roleName;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
