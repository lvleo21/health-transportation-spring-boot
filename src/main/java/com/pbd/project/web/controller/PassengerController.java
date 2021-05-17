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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
