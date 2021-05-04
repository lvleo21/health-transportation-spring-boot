package com.pbd.project.web.controller;

import com.pbd.project.domain.User;
import com.pbd.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String getAllUsers(ModelMap model){
        model.addAttribute("users", userService.findByStaff(false));
        return "user/list";
    }

}
