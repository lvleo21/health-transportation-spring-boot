package com.pbd.project.web.controller;

import com.pbd.project.domain.Prefecture;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.prefecture.PrefectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/prefecture")
public class PrefectureController {

    @Autowired
    private PrefectureService prefectureService;


    @GetMapping("/list")
    public String getAllPrefectures(ModelMap modelMap){

        modelMap.addAttribute("prefectures", prefectureService.findAll());

        return "prefecture/list";
    }

    @GetMapping("/register")
    public String viewRegisterPrefecture(Prefecture prefecture){
        return "prefecture/create";
    }

    @PostMapping("/register/save")
    public String savePrefecture(@Valid Prefecture prefecture, BindingResult result, RedirectAttributes attr){

        if(result.hasErrors()){
            return "prefecture/create";
        }

        prefectureService.save(prefecture);

        attr.addFlashAttribute("success", "Prefeitura adicionada com sucesso.");
        return "redirect:/prefecture/list";

    }

    @GetMapping("/delete/{id}")
    public String deletePrefecture(@PathVariable("id") Long id, RedirectAttributes attr){

        prefectureService.delete(id);
        attr.addFlashAttribute("success", "Prefeitura removida com sucesso.");


        return "redirect:/prefecture/list";

    }

    @ModelAttribute("ufs")
    public UF[] getUFs(){
        return UF.values();
    }


}
