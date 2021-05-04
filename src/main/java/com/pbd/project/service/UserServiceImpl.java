package com.pbd.project.service;

import com.pbd.project.dao.RoleDao;
import com.pbd.project.dao.UserDao;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User save(User user) {

        user.setPassword(bCryptPasswordEncoder.encode((user.getPassword())));
//        user.setActive(true);
//        user.setEmail("verasleonardo210@gmail.com");
//        LocalDate date = LocalDate.now();
//        user.setCreatedAt(date);
//        user.setName("Leonardo Veras Mascena Oliveira Lopes");
//
//        Role roleAdmin = roleDao.findByRole("ADMIN");
//        Role roleGestor = roleDao.findByRole("GESTOR");
//        Role roleOperador = roleDao.findByRole("OPERADOR");
//
//        user.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleGestor, roleOperador)));
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> findByStaff(boolean isStaff) {
        return userDao.findByStaff(isStaff);
    }
}
