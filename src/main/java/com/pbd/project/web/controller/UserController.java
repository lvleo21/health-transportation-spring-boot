package com.pbd.project.web.controller;

import com.pbd.project.domain.ChangePassword;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import com.pbd.project.dto.UserEmployeeDto;
import com.pbd.project.service.UserService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.role.RoleService;
import com.pbd.project.web.validation.EmpĺoyeeValidator;
import com.pbd.project.web.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.sql.SQLException;
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

    @InitBinder("userValidator")
    protected void userInitBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }

    @InitBinder("employeeValidator")
    protected void employeeInitBinder(WebDataBinder binder) {
        binder.setValidator(employeeValidator);
    }


    @GetMapping("")
    public String usersListView(ModelMap model) {
        model.addAttribute("users", userService.findByStaff(false));
        return "user/list";
    }

    @GetMapping("/create")
    public String userCreateView(User user, ModelMap model) {
        model.addAttribute("createView", true);
        return "user/create";
    }

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


    //! Update do Funcionario (Gestor e Operador)
    @GetMapping("employee/update/{id}")
    public String employeeUpdateView(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.findById(id);

        UserEmployeeDto employee = new UserEmployeeDto(
                user.getName(),
                user.getEnrollment(),
                user.getUsername(),
                user.getEmail(),
                user.getActive()
        );

        model.addAttribute("employee", employee);
        model.addAttribute("id", id);
        model.addAttribute("createView", false);
        return "user/employee/update";
    }

    @PostMapping("/update/{id}/save")
    public String employeeUpdateSave(@PathVariable("id") Long id, @Valid UserEmployeeDto employee, BindingResult result, ModelMap model, RedirectAttributes attr) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("createView", false);
            model.addAttribute("id", id);
            return "user/employee/update";
        }

        User user = userService.findById(id);

        user.setName(employee.getName());
        user.setEnrollment(employee.getEnrollment());
        user.setUsername(employee.getUsername());
        user.setEmail(employee.getEmail());
        user.setActive(employee.isActive());

        try {
            userService.update(user);
            attr.addFlashAttribute("success", "Usuário editado com sucesso.");
            return "redirect:/users";

        } catch (DataIntegrityViolationException e) {
            System.out.println("IMPRIMINDO ERROR: " + e.getMostSpecificCause().getMessage());
            model.addAttribute("employee", employee);
            model.addAttribute("id", id);
            model.addAttribute("createView", false); 
            return "user/employee/update";
        }

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
