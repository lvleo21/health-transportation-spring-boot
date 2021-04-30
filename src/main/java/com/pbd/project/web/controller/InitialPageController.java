package com.pbd.project.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitialPageController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
}
