package com.pbd.project.dao.orderResetPassword;

import com.pbd.project.domain.OrderResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderResetPasswordDao extends JpaRepository<OrderResetPassword, Long> {


    //! Trás todos os OrderResetPassword respectivos ao centro de saúde do usuário que esta logado;
    @Query(value = "select orp.* from order_reset_passwords orp, users u " +
            "where id_user_who_requested != ?2 and u.health_center_id = ?1 " +
            "and u.id = id_user_who_requested " +
            "order by orp.request_on desc", nativeQuery = true)
    List<OrderResetPassword> findByHealthCenters(Long idHealthCenter, Long idUser);

    //! Trás todos OrderResetPassword daquele usuário e que estão ativos;
    @Query(value = "select * from order_reset_passwords where id_user_who_requested = ?1 and completed_in is null;", nativeQuery = true)
    List<OrderResetPassword> findActivesByUser(Long id);

    //! Trás todos os Order ResetPassword;
    @Query(value = "select * from order_reset_passwords order by request_on desc;", nativeQuery = true)
    List<OrderResetPassword> findAll();

}
