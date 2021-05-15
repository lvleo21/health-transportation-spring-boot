package com.pbd.project.web.controller.user;

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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private HttpServletRequest request;

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


    @GetMapping("/create")
    public String userCreateView(User user, ModelMap model) {
        model.addAttribute("createView", true);
        return "user/create";
    }

    @PostMapping("/create/save")
    public String userCreateView(@Valid User user, BindingResult result, RedirectAttributes attr, ModelMap model) {

        if (result.hasErrors()) {
            System.out.println("Entrou no has error");
            model.addAttribute("createView", true);
            return "user/create";
        }

        userService.save(user);
        attr.addFlashAttribute("success", "Usuário(a) cadastrado(a) com sucesso.");
        return "redirect:/users";

    }


    @GetMapping("/update/{username}")
    public String userUpdateView(@PathVariable("username") String username, ModelMap model) {
        User user = userService.findByUsername(username);


        model.addAttribute("user", user);
        model.addAttribute("createView", false);
        return "user/create";
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
        attr.addFlashAttribute("success", "Usuário <b>" + user.getUsername() + "</b> editado com sucesso.");
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes attr) {
        userService.delete(id);
        attr.addFlashAttribute("success", "Usuário removido com sucesso.");
        return "redirect:/users";
    }


    @ModelAttribute("roles")
    public List<Role> getRoles() {

        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            return roleService.findAll();
        } else {
            List<Role> roles = new ArrayList<>();
            String path = request.getRequestURI();

            if (path.contains("/update/authenticated-user")) {
                roles.addAll(user.getRoles());
            } else {
                roles.add(roleService.findByRole("OPERADOR"));
            }
            return roles;
        }
    }


    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters() {
        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            return healthCenterService.findAll();
        } else {
            List<HealthCenter> healthCenters = new ArrayList<>();
            HealthCenter healthCenter = healthCenterService.findById(user.getHealthCenter().getId());
            healthCenters.add(healthCenter);

            return healthCenters;
        }
    }

}
