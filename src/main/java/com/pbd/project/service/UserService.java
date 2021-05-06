package com.pbd.project.service;

import com.pbd.project.domain.ChangePassword;
import com.pbd.project.domain.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User save(User user);
    User update(User user);
    User findById(Long id);
    User getUserAuthenticated();
    List<User> findAll();
    List<User> findByStaff(boolean isStaff);


    void changePassword(ChangePassword changePassword, User user);
}
