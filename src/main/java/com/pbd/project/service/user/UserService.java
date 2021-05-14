package com.pbd.project.service.user;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.dto.ChangePassword;
import com.pbd.project.domain.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByEnrollment(String enrollment, HealthCenter healthCenter);
    User findById(Long id);
    List<User> findByStaff(boolean isStaff);
    List<User> findAll();

    User save(User user);
    User update(User user);
    User getUserAuthenticated();
    void changePassword(ChangePassword changePassword, User user);
    void delete(Long id);
    String resetPassword(User user);
}
