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
    public String driverListView(ModelMap model){

        User user = userService.getUserAuthenticated();

        if(user.getStaff()){
            model.addAttribute("drivers", driverService.getAllDrivers());
        } else {
            model.addAttribute("drivers", driverService.getAllDriversByHealthCenter(user.getHealthCenter()));
        }

        return "driver/list";
    }

    @GetMapping("/create")
    public String driverCreateView(Driver driver, ModelMap model){
        model.addAttribute("createView", true);
        return "driver/createOrUpdate";
    }

    @PostMapping("/create/save")
    public String driverCreateSave(@Valid Driver driver, BindingResult result, RedirectAttributes attr, ModelMap model){

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

        Driver driver = driverService.findDriverById(id);
        String url;

        if(this.hasPermission(driver.getHealthCenter().getId())){
            model.addAttribute("driver", driver);
            model.addAttribute("createView", false);
            url = "driver/createOrUpdate";
        } else{
            attr.addFlashAttribute("error", "Você não tem permissões para editar este veículo.");
            url = "redirect:/drivers";
        }

        return url;
    }

    @PostMapping("/update/{id}/save")
    public String driverUpdateSave(@Valid Driver driver, BindingResult result, RedirectAttributes attr, ModelMap model){
        String url ;

        if(this.hasPermission(driver.getHealthCenter().getId())){
            if (result.hasErrors()){
                model.addAttribute("createView", false);
                url = "driver/createOrUpdate";
            }

            driverService.save(driver);

            attr.addFlashAttribute("success", "<b>" + driver.getName() +"</b> atualizado com sucesso.");
            url = "redirect:/drivers";
        } else {
            attr.addFlashAttribute("error", "Você não tem permissões para editar este veículo.");
            url = "redirect:/drivers";
        }

        return url ;
    }

    @GetMapping("delete/{id}")
    public String deleteDriver(@PathVariable("id") Long id, RedirectAttributes attr){
        String url;
        Driver driver = driverService.findDriverById(id);

        if(this.hasPermission(driver.getHealthCenter().getId())){
            driverService.delete(id);
            attr.addFlashAttribute("success", "Motorista deletado com sucesso.");
            return  "redirect:/drivers";
        } else {
            attr.addFlashAttribute("error", "Você não tem permissões para deletar este veículo.");
            url = "redirect:/drivers";
        }

        return url;
    }

    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters() {
        return healthCenterService.getModelAttribute();
    }

    public boolean hasPermission(Long idDriverHealthCenter){
        User user = userService.getUserAuthenticated();
        return (user.getStaff() || (user.getHealthCenter().getId().equals(idDriverHealthCenter))) ? true : false;
    }
}


