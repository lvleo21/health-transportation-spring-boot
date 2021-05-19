package com.pbd.project.configuration;

import com.pbd.project.dao.address.AddressDao;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import com.pbd.project.domain.enums.Gender;
import com.pbd.project.service.address.AddressService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.passenger.PassengerService;
import com.pbd.project.service.user.UserService;
import com.pbd.project.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

@Component
public class InitialAdmin implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    public void run(ApplicationArguments args) throws Exception {
        Role roleAdmin = roleService.findByRole("ADMIN");


        if (roleAdmin.getUsers().isEmpty()) {

            Properties prop = getProperties();

            Role roleGestor = roleService.findByRole("GESTOR");
            Role roleOperador = roleService.findByRole("OPERADOR");
            User user = new User();
            user.setUsername(prop.getProperty("username"));
            user.setPassword(prop.getProperty("password"));
            user.setActive(true);
            user.setStaff(true);
            user.setEmail(prop.getProperty("email"));
            user.setName(prop.getProperty("name"));
            user.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleGestor, roleOperador)));
            userService.save(user);
        }


    }

    private static Properties getProperties() throws IOException {
        Properties prop = new Properties();
        String path = "./admin.properties";
        prop.load(InitialAdmin.class.getClassLoader().getResourceAsStream(path));

        return prop;
    }





}
