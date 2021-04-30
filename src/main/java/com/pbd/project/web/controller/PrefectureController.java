package com.pbd.project.web.controller;

import com.pbd.project.domain.Prefecture;
import com.pbd.project.domain.enums.UF;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/prefecture")
public class PrefectureController {




    @GetMapping("/list")
    public String getAllPrefectures(){
        return "prefecture/list";
    }

    @GetMapping("/register")
    public String viewRegisterPrefecture(Prefecture prefecture){
        return "prefecture/create";
    }

    @PostMapping("/register/save")
    public String savePrefecture(@Valid Prefecture prefecture, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "/register";
        }


        attr.addFlashAttribute("success", "Prefeitura inserida com sucesso.");
        return "redirect:/prefecture/list";

    }

    @ModelAttribute("ufs")
    public UF[] getUFs(){
        return UF.values();
    }


}
