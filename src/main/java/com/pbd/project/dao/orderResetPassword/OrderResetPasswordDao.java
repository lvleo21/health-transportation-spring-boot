package com.pbd.project.dao.orderResetPassword;

import com.pbd.project.domain.OrderResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderResetPasswordDao extends JpaRepository<OrderResetPassword, Long> {

    @Query(value = "select orp.* from order_reset_passwords orp, users u " +
            "where id_user_who_requested != ?2 and u.health_center_id = ?1 " +
            "order by orp.request_on desc", nativeQuery = true)
    List<OrderResetPassword> findByHealthCenters(Long idHealthCenter, Long idUser);

    @Query(value = "select * from order_reset_passwords where id_user_who_requested = ?1 and completed_in is null;", nativeQuery = true)
    List<OrderResetPassword> findActivesByUser(Long id);

    @Query(value = "select * from order_reset_passwords order by request_on desc;", nativeQuery = true)
    List<OrderResetPassword> findAll();

}
