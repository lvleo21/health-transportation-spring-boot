package com.pbd.project.web.controller;


import com.pbd.project.domain.views.TravelsPerMonthViews;
import com.pbd.project.service.travelPerMonth.TravelsPerMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class InitialPageController {

    @Autowired
    private TravelsPerMonthService service;

    @GetMapping("/")
    public String home(ModelMap model){

        List<TravelsPerMonthViews> travelsPerMonthViewsList = service.findAll();

        model.addAttribute("tpm", travelsPerMonthViewsList);
        model.addAttribute("year", LocalDate.now().getYear());

        return "home";
    }
}
