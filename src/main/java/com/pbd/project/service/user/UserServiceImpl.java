package com.pbd.project.service.user;

import com.pbd.project.dao.role.RoleDao;
import com.pbd.project.dao.user.UserDao;
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

        if(user.getStaff()){
            return userDao.findUsers(user.getId());
        }else{
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


    public String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }


}
