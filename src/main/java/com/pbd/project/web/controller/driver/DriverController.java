package com.pbd.project.web.controller.driver;


import com.pbd.project.domain.*;
import com.pbd.project.service.driver.DriverService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.role.RoleService;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;

    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String driverListView(ModelMap model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("name") Optional<String> name) {

        User user = userService.getUserAuthenticated();
        int currentPage = page.orElse(0);
        String driverName = name.orElse(null);
        Page<Driver> drivers = driverService.getDrivers(
                currentPage, driverName, user.getStaff(), user.getHealthCenter());

        model.addAttribute("driverName", driverName);
        model.addAttribute("drivers", drivers);
        model.addAttribute("isSearch", driverName != null ? true : false);
        model.addAttribute("queryIsEmpty", drivers.isEmpty());

        return "driver/list";
    }

    @GetMapping("/create")
    public String driverCreateView(Driver driver, ModelMap model) {
        model.addAttribute("createView", true);
        return "driver/createOrUpdate";
    }

    @PostMapping("/create/save")
    public String driverCreateSave(@Valid Driver driver, BindingResult result, RedirectAttributes attr, ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("createView", true);
            return "driver/createOrUpdate";
        }

        driverService.save(driver);
        attr.addFlashAttribute("success", "<b>" + driver.getName() + "</b> adicionado com sucesso.");

        return "redirect:/drivers";

    }

    @GetMapping("/update/{id}")
    public String driverUpdateView(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {

        Driver driver = driverService.findDriverById(id);
        String url;

        if (this.hasPermission(driver.getHealthCenter().getId())) {
            model.addAttribute("driver", driver);
            model.addAttribute("createView", false);
            url = "driver/createOrUpdate";
        } else {
            attr.addFlashAttribute("error", "Você não tem permissões para editar este motorista.");
            url = "redirect:/drivers";
        }

        return url;
    }

    @PostMapping("/update/{id}/save")
    public String driverUpdateSave(@Valid Driver driver, BindingResult result, RedirectAttributes attr, ModelMap model) {
        String url;

        if (this.hasPermission(driver.getHealthCenter().getId())) {
            if (result.hasErrors()) {
                model.addAttribute("createView", false);
                url = "driver/createOrUpdate";
            }

            driverService.save(driver);

            attr.addFlashAttribute("success", "<b>" + driver.getName() + "</b> atualizado(a) com sucesso.");
            url = "redirect:/drivers";
        } else {
            attr.addFlashAttribute("error", "Você não tem permissões para editar este motorista.");
            url = "redirect:/drivers";
        }

        return url;
    }

    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable("id") Long id, RedirectAttributes attr) {
        Driver driver = driverService.findDriverById(id);

        if (this.hasPermission(driver.getHealthCenter().getId())) {
            driverService.delete(id);
            attr.addFlashAttribute("success", "Motorista deletado com sucesso.");
        } else {
            attr.addFlashAttribute("error", "Você não tem permissões para deletar este motorista.");
        }

        return "redirect:/drivers";
    }

    @GetMapping("/{id}/change-status")
    public String changeDriverActive(@PathVariable("id") Long id, RedirectAttributes attr){

        Driver driver = driverService.findDriverById(id);

        if (this.hasPermission(driver.getHealthCenter().getId())) {
            driverService.changeActive(driver);
            attr.addFlashAttribute("success", "Motorista <b>"+ driver.getName() +
                    "</b> modificado com sucesso.");

        } else {
            attr.addFlashAttribute("error", "Você não tem permissões para modificar este motorista.");
        }

        return "redirect:/drivers";
    }

    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters() {
        return healthCenterService.getModelAttribute();
    }

    public boolean hasPermission(Long idDriverHealthCenter) {
        User user = userService.getUserAuthenticated();
        return (user.getStaff() || (user.getHealthCenter().getId().equals(idDriverHealthCenter))) ? true : false;
    }
}


