package com.pbd.project.web.controller;

import com.pbd.project.domain.Passenger;
import com.pbd.project.domain.User;
import com.pbd.project.service.passenger.PassengerService;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private UserService userService;

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
}
