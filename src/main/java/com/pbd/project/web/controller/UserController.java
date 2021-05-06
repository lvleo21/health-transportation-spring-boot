package com.pbd.project.web.controller;

import com.pbd.project.domain.ChangePassword;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import com.pbd.project.service.UserService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.role.RoleService;
import com.pbd.project.web.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new UserValidator());
    }

    @GetMapping("/list")
    public String getAllUsers(ModelMap model) {
        model.addAttribute("users", userService.findByStaff(false));
        return "user/list";
    }

    @GetMapping("/register")
    public String createUserView(User user) {
        return "user/create";
    }

    @PostMapping("/register/save")
    public String saveUser(@Valid User user, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            return "user/create";
        }

        userService.save(user);
        attr.addFlashAttribute("success", "Usuário(a) cadastrado(a) com sucesso.");
        return "redirect:/users/list";

    }

    @GetMapping("/update/{id}")
    public String updateUserView(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);

        return "user/create";
    }

    @GetMapping("/update/myself")
    public String updateUserView(ModelMap model) {
        User user = userService.getUserAuthenticated();

        model.addAttribute("user", user);

        return "user/create";
    }

    @PostMapping("/update/save")
    public String updateUserSave(@Valid User user, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "user/create";
        }

        userService.update(user);

        attr.addFlashAttribute("success", "Centro de saúde editado com sucesso.");
        return "redirect:/users/list";
    }


    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters() {
        return healthCenterService.findAll();
    }

}
