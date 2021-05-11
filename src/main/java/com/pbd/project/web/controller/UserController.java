package com.pbd.project.web.controller;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import com.pbd.project.dto.Employee;
import com.pbd.project.service.user.UserService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.role.RoleService;
import com.pbd.project.web.validation.EmpĺoyeeValidator;
import com.pbd.project.web.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EmpĺoyeeValidator employeeValidator;

    @InitBinder("user")
    public void userInitBinder(WebDataBinder binder) {
        binder.setValidator(this.userValidator);
    }

    @InitBinder("employee")
    public void employeeInitBinder(WebDataBinder binder) {
        binder.setValidator(this.employeeValidator);
    }

    @GetMapping("")
    public String usersListView(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    //! Método de CREATE: ADMIN
    @GetMapping("/create")
    public String userCreateView(User user, ModelMap model) {
        model.addAttribute("createView", true);
        return "user/create";
    }

    //! Método de CREATE: ADMIN
    @PostMapping("/create/save")
    public String userCreateView(@Valid User user, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("createView", true);
            return "user/create";
        }

        userService.save(user);
        attr.addFlashAttribute("success", "Usuário(a) cadastrado(a) com sucesso.");
        return "redirect:/users";

    }


    //! Método de UPDATE: GESTOR E OPERADOR
    @GetMapping("employee/update/{id}")
    public String employeeUpdateView(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.findById(id);

        Employee employee = new Employee();
        employee.toMe(user);

        model.addAttribute("employee", employee);
        model.addAttribute("id", id);
        model.addAttribute("createView", false);
        return "user/employee/createOrUpdate";
    }
    
    //! Método de UPDATE: GESTOR E OPERADOR
    @PostMapping("/update/{id}/save")
    public String employeeUpdateSave(@PathVariable("id") Long id,
                                     @Valid Employee employee,
                                     BindingResult result,
                                     ModelMap model, RedirectAttributes attr) {

        if (result.hasErrors()) {
            model.addAttribute("createView", false);
            model.addAttribute("employee", employee);
            model.addAttribute("id", id);
            return "user/employee/createOrUpdate";
        }

        User user = userService.findById(id);
        user.toMe(employee);

        userService.update(user);
        attr.addFlashAttribute("success", "Usuário editado com sucesso.");
        return "redirect:/users";
    }

    @GetMapping("/update/authenticated-user")
    public String updateUserView(ModelMap model) {
        User user = userService.getUserAuthenticated();
        model.addAttribute("user", user);
        model.addAttribute("createView", false);
        return "user/create";
    }

    @PostMapping("/update/save")
    public String updateUserSave(@Valid User user, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("createView", false);
            return "user/create";
        }

        userService.update(user);
        attr.addFlashAttribute("success", "Usuário editado com sucesso.");
        return "redirect:/users";
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
