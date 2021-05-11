package com.pbd.project.dao.user;

import com.pbd.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByEnrollment(String enrollment);
    List<User> findByStaff(boolean isStaff);

}
