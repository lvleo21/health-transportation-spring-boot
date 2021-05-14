package com.pbd.project.service.orderResetPassword;

import com.pbd.project.dao.orderResetPassword.OrderResetPasswordDao;
import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.domain.User;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderResetPasswordServiceImpl implements OrderResetPasswordService{
    @Autowired
    private OrderResetPasswordDao dao;

    @Autowired
    private UserService userService;

    @Override
    public void create(User user) {
        OrderResetPassword orderResetPassword = new OrderResetPassword();
        orderResetPassword.setUserWhoRequested(user);

        dao.save(orderResetPassword);
    }

    @Override
    public void update(OrderResetPassword orderResetPassword) {
        orderResetPassword.setUserWhoSolvedOrder(userService.getUserAuthenticated());
        orderResetPassword.setCompletedIn(LocalDate.now());
        dao.save(orderResetPassword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResetPassword> findByHealthCenters(Long id) {
        return dao.findByHealthCenters(id, userService.getUserAuthenticated().getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResetPassword> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResetPassword> findActivesByUser(Long id) {
        return dao.findActivesByUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResetPassword findById(Long id) {
        return dao.findById(id).get();
    }


}
