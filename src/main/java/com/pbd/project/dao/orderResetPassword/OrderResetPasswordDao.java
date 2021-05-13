package com.pbd.project.dao.orderResetPassword;

import com.pbd.project.domain.OrderResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderResetPasswordDao extends JpaRepository<OrderResetPassword, Long> {
}
