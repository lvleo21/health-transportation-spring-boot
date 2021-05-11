package com.pbd.project.service.user;

import com.pbd.project.domain.ChangePassword;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByEnrollment(String enrollment);
    User save(User user);
    User update(User user);
    User findById(Long id);
    User getUserAuthenticated();
    List<User> findAll();
    List<User> findByStaff(boolean isStaff);


    void changePassword(ChangePassword changePassword, User user);
}
