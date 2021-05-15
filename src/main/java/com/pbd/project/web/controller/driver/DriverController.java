package com.pbd.project.web.controller.driver;


import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.User;
import com.pbd.project.domain.Vehicle;
import com.pbd.project.service.driver.DriverService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;

    @Autowired
    private HealthCenterService healthCenterService;



    @GetMapping("")
    public String driversListView(ModelMap model){

        User user = userService.getUserAuthenticated();

        if(user.getStaff()){
            model.addAttribute("drivers", driverService.findAll());
        } else {
            model.addAttribute("drivers", driverService.findByHealthcenter(user.getHealthCenter()));
        }

        return "driver/list";
    }

    @GetMapping("/create")
    public String driversCreateView(Driver driver, ModelMap model){
        model.addAttribute("createView", true);
        return "driver/createOrUpdate";
    }

    @PostMapping("/create/save")
    public String createDriver(@Valid Driver driver, BindingResult result, RedirectAttributes attr, ModelMap model){

        if(result.hasErrors()){
            model.addAttribute("createView", true);
            return "driver/createOrUpdate";
        }

        driverService.save(driver);
        attr.addFlashAttribute("success", "<b>" + driver.getName() +"</b> adicionado com sucesso.");

        return  "redirect:/drivers";

    }

    @GetMapping("/update/{id}")
    public String driverUpdateView(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr){

        Driver driver = driverService.findById(id);
        User user = userService.getUserAuthenticated();


        if(!user.getStaff()){
            if (!user.getHealthCenter().getId().equals(driver.getHealthCenter().getId())){
                attr.addFlashAttribute("error", "Você não tem permissões para editar este veículo.");
                return  "redirect:/drivers";
            }
        }

        model.addAttribute("driver", driver);
        model.addAttribute("createView", false);
        return "driver/createOrUpdate";
    }

    @PostMapping("/update/{id}/save")
    public String driverUpdateSave(@Valid Driver driver, BindingResult result, RedirectAttributes attr, ModelMap model){

        if (result.hasErrors()){
            model.addAttribute("createView", false);
            return "driver/createOrUpdate";
        }

        driverService.save(driver);

        attr.addFlashAttribute("success", "<b>" + driver.getName() +"</b> atualizado com sucesso.");
        return  "redirect:/drivers";
    }

    @GetMapping("delete/{id}")
    public String deleteDriver(@PathVariable("id") Long id, RedirectAttributes attr){

        driverService.delete(id);
        attr.addFlashAttribute("success", "Motorista deletado com sucesso.");
        return  "redirect:/drivers";
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
