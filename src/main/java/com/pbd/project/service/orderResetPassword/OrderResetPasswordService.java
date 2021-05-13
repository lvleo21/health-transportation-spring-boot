package com.pbd.project.service.orderResetPassword;

import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.domain.User;

import java.util.List;

public interface OrderResetPasswordService {
    void create(User user);
    void update(OrderResetPassword orderResetPassword);
    List<OrderResetPassword> findByHealthCenters(Long id);
    List<OrderResetPassword> findAll();
    List<OrderResetPassword> findActivesByUser(Long id);
    OrderResetPassword findById(Long id);
}
