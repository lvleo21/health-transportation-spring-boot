package com.pbd.project.service.orderResetPassword;

import com.pbd.project.dao.orderResetPassword.OrderResetPasswordDao;
import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderResetPasswordServiceImpl implements OrderResetPasswordService{
    @Autowired
    private OrderResetPasswordDao dao;

    @Override
    public void create(User user) {

        OrderResetPassword orderResetPassword = new OrderResetPassword();
        orderResetPassword.setUserWhoRequested(user);

        dao.save(orderResetPassword);
    }
}
