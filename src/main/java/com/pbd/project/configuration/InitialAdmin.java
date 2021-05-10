package com.pbd.project.configuration;

import com.pbd.project.dao.role.RoleDao;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import com.pbd.project.service.UserService;
import com.pbd.project.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class InitialAdmin implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public void run(ApplicationArguments args) throws Exception {
        Role roleAdmin = roleService.findByRole("ADMIN");

        if (roleAdmin.getUsers().isEmpty()){
            Role roleGestor = roleService.findByRole("GESTOR");
            Role roleOperador = roleService.findByRole("OPERADOR");
            User user = new User();
            user.setUsername("lvleo21");
            user.setPassword("Leo10272109");
            user.setActive(true);
            user.setStaff(true);
            user.setEmail("verasleonardo210@gmail.com");
            user.setName("Leonardo Veras Mascena Oliveira Lopes");
            user.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleGestor, roleOperador)));
            userService.save(user);
        }

    }
}
