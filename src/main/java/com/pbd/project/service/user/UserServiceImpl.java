package com.pbd.project.service.user;

import com.pbd.project.dao.role.RoleDao;
import com.pbd.project.dao.user.UserDao;
import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.dto.ChangePassword;
import com.pbd.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Transactional
@Service
public class UserServiceImpl implements UserService {

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
    @Transactional(readOnly = true)
    public User findByEnrollment(String enrollment) {
        return userDao.findByEnrollment(enrollment);
    }

    @Override
    public User save(User user) {
        user.setPassword(this.encodePassword(user.getPassword()));
        user.setCreatedAt(LocalDate.now());

        if (user.getStaff() == null) {
            user.setStaff(false);
        }

        return userDao.save(user);
    }


    public User update(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        User user = this.getUserAuthenticated();

        if (user.getStaff()) {
            return userDao.findUsers(user.getId());
        } else {
            return userDao.findUsers(user.getHealthCenter().getId(), user.getId());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByStaff(boolean isStaff) {
        return userDao.findByStaff(isStaff);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return this.findByUsername(auth.getName());
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public void changePassword(ChangePassword changePassword, User user) {
        user.setPassword(this.encodePassword(changePassword.getNewPassword()));
        this.update(user);
    }

    @Override
    public String resetPassword(User user) {
        try {
            String newPassword = generatePassword();

            user.setPassword(this.encodePassword(newPassword));
            this.update(user);
            return newPassword;
        } catch (Exception e){
            return "";
        }
    }

    public String generatePassword() {
        int len = 11;
        char[] chart = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z'};

        String password;

        while (true) {
            char[] senha = new char[len];

            int chartLenght = chart.length;
            Random rdm = new Random();

            for (int x = 0; x < len; x++)
                senha[x] = chart[rdm.nextInt(chartLenght)];

            password = new String(senha);

            if (!password.matches("^[^\\d]+$") && !password.matches("^[^A-Z]+$") && !password.matches("^[^a-z]+$")) {
                break;
            }
        }

        return password;
    }


    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }


}
