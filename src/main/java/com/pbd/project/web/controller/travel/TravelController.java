package com.pbd.project.web.controller.travel;

import com.pbd.project.domain.*;
import com.pbd.project.domain.enums.TravelStatus;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.driver.DriverService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.travel.TravelService;
import com.pbd.project.service.user.UserService;
import com.pbd.project.service.vehicle.VehicleService;
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
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private UserService userService;

    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("")
    public String travelsListView(ModelMap model) {

        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            model.addAttribute("travels", travelService.findAll());
        } else {
            model.addAttribute("travels", travelService.findByHealthCenter(user.getHealthCenter()));
        }

        return "travel/list";
    }

    @GetMapping("/create")
    public String travelsCreateView(Travel travel, ModelMap model) {
        model.addAttribute("createView", true);
        return "travel/createOrUpdate";
    }

    @PostMapping("/create/save")
    public String createTravel(@Valid Travel travel, BindingResult result, RedirectAttributes attr, ModelMap model){

        if(result.hasErrors()){
            model.addAttribute("createView", true);
            return "travel/createOrUpdate";
        }

        travelService.save(travel);
        attr.addFlashAttribute("success", "Viagem adicionada com sucesso.");

        return  "redirect:/travels";
    }

    @GetMapping("/update/{id}")
    public String travelUpdateView(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr){

        Travel travel = travelService.findById(id);
        User user = userService.getUserAuthenticated();


        if(!user.getStaff()){
            if (!user.getHealthCenter().getId().equals(travel.getHealthCenter().getId())){
                attr.addFlashAttribute("error", "Você não tem permissões para editar este veículo.");
                return  "redirect:/travels";
            }
        }

        model.addAttribute("travel", travel);
        model.addAttribute("createView", false);
        return "travel/createOrUpdate";
    }

    @PostMapping("/update/{id}/save")
    public String travelUpdateSave(@Valid Travel travel, BindingResult result, RedirectAttributes attr, ModelMap model){

        if (result.hasErrors()){
            model.addAttribute("createView", false);
            return "travel/createOrUpdate";
        }

        travelService.update(travel);

        attr.addFlashAttribute("success", "Viagem atualizada com sucesso.");
        return  "redirect:/travels";
    }

    @GetMapping("delete/{id}")
    public String deleteTravel(@PathVariable("id") Long id, RedirectAttributes attr){

        Travel travel = travelService.findById(id);


        if (travel.getRegisteredPassengers() == 0) {
            travelService.delete(travel);
            attr.addFlashAttribute("success", "Viagem deletada com sucesso.");
        }

        else{
            attr.addFlashAttribute("error", "Está viagem possue passageiros cadastrados.");
        }



        return  "redirect:/travels";
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

    @ModelAttribute("drivers")
    public List<Driver> drivers() {
        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            return driverService.findAll();
        } else {
            return driverService.findAvailable(
                    user.getHealthCenter().getId(),
                    true,
                    true
            );
        }
    }

    @ModelAttribute("vehicles")
    public List<Vehicle> vehicles() {
        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            return vehicleService.findAll();
        } else {
            return vehicleService.findAvailable(
                    user.getHealthCenter().getId(),
                    true,
                    true
            );
        }
    }

    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }

    @ModelAttribute("travelStatus")
    public TravelStatus[] getTravelStatus() {
        return TravelStatus.values();
    }

}
