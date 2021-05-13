package com.pbd.project.web.controller;


import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.domain.User;
import com.pbd.project.dto.ResetPassword;
import com.pbd.project.service.orderResetPassword.OrderResetPasswordService;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderResetPasswordService orderResetPasswordService;

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/forgout-password")
    public String forgoutPassword(ResetPassword resetPassword){
        return "forgout-password";
    }

    @PostMapping("/forgout-password/save")
    public String save(@Valid ResetPassword resetPassword, BindingResult result, ModelMap model, RedirectAttributes attr){
        if(result.hasErrors()){
            return "forgout-password";
        }

        User user = userService.findByEmail(resetPassword.getEmail());

        if(user == null){
            model.addAttribute("error",
                    "* Email inválido ou inexistente, tente novamente.");
            return "forgout-password";
        }

        if(!orderResetPasswordService.findActivesByUser(user.getId()).isEmpty()){
            model.addAttribute("error",
                    "* Você já solicitou este procedimento, aguarde a verificação.");
            return "forgout-password";
        }


        orderResetPasswordService.create(user);


        attr.addFlashAttribute("success",
                "Aguarde a analise da sua solicitação e verifique seu email.");
        return "redirect:/forgout-password";

    }




}
