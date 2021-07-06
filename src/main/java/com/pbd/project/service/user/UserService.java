package com.pbd.project.service.user;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.dto.ChangePassword;
import com.pbd.project.domain.User;
import org.springframework.data.domain.Page;

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

    void changeStatus(User user);


    Page<User> getAll(int currentPage);
    Page<User> getAllByEnrollment(int currentPage, String enrollment);
    Page<User> getAllByHealthCenter(int currentPage, HealthCenter healthCenter);
    Page<User> getAllByHealthCenterAndEnrollment(int currentPage,
                                                 HealthCenter healthCenter,
                                                 String enrollment);

}
