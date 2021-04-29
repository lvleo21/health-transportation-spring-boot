package com.pbd.project.service;

import com.pbd.project.domain.User;

public interface UserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User save(User user);
}
