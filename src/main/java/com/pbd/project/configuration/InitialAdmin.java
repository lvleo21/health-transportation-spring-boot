package com.pbd.project.configuration;

import com.pbd.project.dao.RoleDao;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import com.pbd.project.service.UserService;
import com.pbd.project.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitialAdmin implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    public void run(ApplicationArguments args) throws Exception {
        Role roleAdmin = roleDao.findByRole("ADMIN");


        if (roleAdmin.getUsers().isEmpty()){
            Role roleGestor = roleDao.findByRole("GESTOR");
            Role roleOperador = roleDao.findByRole("OPERADOR");
            User user = new User();
            user.setUsername("lvleo21");
            user.setPassword("Leo10272109");
            user.setActive(true);
            user.setEmail("verasleonardo210@gmail.com");
            user.setCreatedAt(LocalDate.now());
            user.setName("Leonardo Veras Mascena Oliveira Lopes");
            user.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleGestor, roleOperador)));
            userService.save(user);
        }

    }
}
