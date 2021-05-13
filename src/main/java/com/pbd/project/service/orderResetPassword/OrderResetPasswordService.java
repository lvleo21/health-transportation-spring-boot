package com.pbd.project.service.orderResetPassword;

import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.domain.User;

public interface OrderResetPasswordService {
    void create(User user);
}
