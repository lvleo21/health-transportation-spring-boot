package com.pbd.project.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prefecture")
public class PrefectureController {


    @GetMapping("/list")
    public String getAllPrefectures(){
        return "prefecture/list";
    }

    @GetMapping("/register")
    public String registerPrefecture(){
        return "prefecture/create";
    }

}
