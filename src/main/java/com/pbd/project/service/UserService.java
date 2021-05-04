package com.pbd.project.service;

import com.pbd.project.domain.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User save(User user);
    List<User> findAll();
    List<User> findByStaff(boolean isStaff);
}
