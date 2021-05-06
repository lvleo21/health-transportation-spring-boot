package com.pbd.project.web.controller;

import com.pbd.project.domain.ChangePassword;
import com.pbd.project.domain.User;
import com.pbd.project.service.UserService;
import com.pbd.project.web.validation.ChangePasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user/change-password")
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChangePasswordValidator changePasswordValidator;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(changePasswordValidator);
    }

    @GetMapping("/")
    public String chagePasswordView(ChangePassword changePassword) {
        return "user/change-password";
    }


    @PostMapping("/save")
    public String chagePasswordSave(@Valid ChangePassword changePassword, BindingResult result, RedirectAttributes attr) {

        if(result.hasErrors()){
            return "user/change-password";
        }

        userService.changePassword(changePassword, userService.getUserAuthenticated());

        attr.addFlashAttribute("success", "Senha alterada com sucesso.");
        return "redirect:/";
    }

}
