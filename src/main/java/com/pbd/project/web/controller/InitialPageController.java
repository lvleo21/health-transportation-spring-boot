package com.pbd.project.web.controller;


import com.pbd.project.domain.User;
import com.pbd.project.domain.views.LocationsPerNeighborhood;
import com.pbd.project.domain.views.TravelsPerMonthViews;
import com.pbd.project.service.locationPerNeighborhood.LPNService;
import com.pbd.project.service.travelPerMonth.TravelsPerMonthService;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class InitialPageController {

    @Autowired
    private TravelsPerMonthService tpmService;

    @Autowired
    private LPNService lnpService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(ModelMap model){
        User user = userService.getUserAuthenticated();


        List<TravelsPerMonthViews> tpm = null;
        List<LocationsPerNeighborhood> lpn = null;

        if (user.getStaff()){

            System.out.println("Entrou");

            tpm = tpmService.findAll();
            lpn = lnpService.findAll();
        } else {
            tpm = tpmService.findByHealthCenterId(Math.toIntExact(user.getHealthCenter().getId()));
            lpn = lnpService.findByHealthCenter(Math.toIntExact(user.getHealthCenter().getId()));
        }





        model.addAttribute("tpm", tpm);
        model.addAttribute("lpn", lpn);
        model.addAttribute("year", LocalDate.now().getYear());

        return "home";
    }
}
