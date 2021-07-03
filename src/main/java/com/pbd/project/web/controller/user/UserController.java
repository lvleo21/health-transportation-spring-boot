package com.pbd.project.web.controller.user;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Log;
import com.pbd.project.domain.Role;
import com.pbd.project.domain.User;
import com.pbd.project.service.user.UserService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.role.RoleService;
import com.pbd.project.web.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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



    @InitBinder("user")
    public void initialBinder(WebDataBinder binder) {
        binder.addValidators(this.userValidator);
    }

    @GetMapping("")
    public String usersListView(ModelMap model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("enrollment") Optional<String> enrollment){

        int currentPage = page.orElse(0);
        String tempEnrollment = enrollment.orElse(null);

        Page<User> users = null;
        User user = userService.getUserAuthenticated();

        if (tempEnrollment != null) {
            users = user.getStaff()
                    ? userService.getAllByEnrollment(currentPage, tempEnrollment)
                    : userService.getAllByHealthCenterAndEnrollment(currentPage, user.getHealthCenter(), tempEnrollment);
        } else {
            users = user.getStaff()
                    ? userService.getAll(currentPage)
                    : userService.getAllByHealthCenter(currentPage, user.getHealthCenter());
        }

        model.addAttribute("users", users);
        model.addAttribute("isSearch", tempEnrollment == null ? false : true);
        model.addAttribute("queryIsEmpty", users.getTotalElements() == 0 ? true : false);
        model.addAttribute("enrollment", tempEnrollment);

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
        return healthCenterService.getModelAttribute();
    }

}
