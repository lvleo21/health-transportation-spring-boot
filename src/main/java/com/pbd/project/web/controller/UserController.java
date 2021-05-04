package com.pbd.project.web.controller;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import com.pbd.project.service.UserService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HealthCenterService healthCenterService;

    @GetMapping("/list")
    public String getAllUsers(ModelMap model){
        model.addAttribute("users", userService.findByStaff(false));
        return "user/list";
    }

    @GetMapping("/register")
    public String createUserView(User user){
        return "user/create";
    }

    @PostMapping("/register/save")
    public String saveUser(@Valid User user, BindingResult result, RedirectAttributes attr){

        if(result.hasErrors()){
            return "user/create";
        }

        userService.save(user);
        attr.addFlashAttribute("success", "Usuário(a) cadastrado(a) com sucesso.");
        return "redirect:/users/list";

    }

    @ModelAttribute("roles")
    public List<Role> getRoles(){
        return roleService.findAll();
    }

    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters(){
        return healthCenterService.findAll();
    }

}
