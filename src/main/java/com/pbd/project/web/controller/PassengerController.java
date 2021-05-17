package com.pbd.project.web.controller;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;
import com.pbd.project.domain.Travel;
import com.pbd.project.domain.User;
import com.pbd.project.domain.enums.Gender;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.passenger.PassengerService;
import com.pbd.project.service.user.UserService;
import com.pbd.project.web.validation.PassengerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private UserService userService;

    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private PassengerValidator passengerValidator;

    @InitBinder
    public void userInitBinder(WebDataBinder binder) {
        binder.setValidator(this.passengerValidator);
    }


    @GetMapping("")
    public String passengersListView(ModelMap model) {

        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            model.addAttribute("passengers", passengerService.findAll());
        } else {
            model.addAttribute("passengers", passengerService.findByHealthCenter(user.getHealthCenter()));
        }

        return "passenger/list";

    }

    @GetMapping("/create")
    public String passengersCreateView(Passenger passenger, ModelMap model) {
        model.addAttribute("createView", true);
        return "passenger/createOrUpdate";
    }

    @PostMapping("/create/save")
    public String createPassenger(@Valid Passenger passenger, BindingResult result, RedirectAttributes attr, ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("createView", true);
            return "passenger/createOrUpdate";
        }

        passengerService.save(passenger);
        attr.addFlashAttribute("success", "<b>"+ passenger.getName() + "</b> adicionado com sucesso.");

        return "redirect:/passengers";
    }

    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }

    @ModelAttribute("genres")
    public Gender[] getGenres() {
        return Gender.values();
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