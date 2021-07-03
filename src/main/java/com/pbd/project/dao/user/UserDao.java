package com.pbd.project.dao.user;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByEnrollment(String enrollment);
    User findByEnrollmentAndHealthCenter(String enrollment, HealthCenter healthCenter);
    User findUsersByEnrollmentAndHealthCenter(String enrollment, HealthCenter healthCenter);
    List<User> findByHealthCenter(HealthCenter healthCenter);
    List<User> findByStaff(boolean isStaff);

    @Query(value = "SELECT * FROM USERS WHERE health_center_id = ?1 EXCEPT SELECT * FROM USERS WHERE id = ?2", nativeQuery = true)
    List<User> findUsers(Long idHealthCenter, Long idUser);
    @Query(value = "SELECT * FROM USERS EXCEPT SELECT * FROM USERS WHERE id = ?1", nativeQuery = true)
    List<User> findUsers(Long idUser);

    //TODO: Buscar todos páginado;
    @Query(value = "SELECT * FROM USERS WHERE id != ?1", nativeQuery = true)
    Page<User> findAllUsers(Pageable pageable, Long idUser);

    //TODO: Buscar por centro de saúde páginado;
    @Query(value = "SELECT * FROM USERS WHERE health_center_id = ?1 and id != ?2", nativeQuery = true)
    Page<User> findUsersByHealthCenter(Pageable pageable, Long idHealthCenter, Long idUser);

    //TODO: Buscar todos páginado + busca;
    @Query(value = "SELECT * FROM USERS WHERE enrollment LIKE concat('%', ?2,'%')" +
            "and id != ?1", nativeQuery = true)
    Page<User> findAllUsersByEnrollment(Pageable pageable, Long idUser, String enrollment);

    //TODO: Buscar por centro de saúde páginado + busca;
    @Query(value = "SELECT * FROM USERS WHERE health_center_id = ?1 AND enrollment LIKE concate('%', ?3, '%' )" +
            "and id != ?2", nativeQuery = true)
    Page<User> findUsersByHealthCenterAndEnrollment(Pageable pageable, Long idHealthCenter,
                                                    Long idUser, String enrollment);
}
