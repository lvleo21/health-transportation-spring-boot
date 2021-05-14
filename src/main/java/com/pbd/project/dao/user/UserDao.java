package com.pbd.project.dao.user;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByEnrollment(String enrollment);
    User findUsersByEnrollmentAndHealthCenter(String enrollment, HealthCenter healthCenter);
    List<User> findByHealthCenter(HealthCenter healthCenter);
    List<User> findByStaff(boolean isStaff);

    @Query(value = "SELECT * FROM USERS WHERE health_center_id = ?1 EXCEPT SELECT * FROM USERS WHERE id = ?2", nativeQuery = true)
    List<User> findUsers(Long idHealthCenter, Long idUser);

    @Query(value = "SELECT * FROM USERS EXCEPT SELECT * FROM USERS WHERE id = ?1", nativeQuery = true)
    List<User> findUsers(Long idUser);

}
