package com.pbd.project.web.controller;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Prefecture;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.prefecture.PrefectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/health-center")
public class HealthCenterController {


    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private PrefectureService prefectureService;


    @GetMapping("/list")
    public String getHealthCenters(ModelMap model){
        model.addAttribute("healthCenters", healthCenterService.findAll());
        return "healthCenter/list";
    }


    @GetMapping("/register")
    public String viewRegisterHealthCenter(HealthCenter healthCenter){ return "healthCenter/create"; }

    @PostMapping("/register/save")
    public String saveHealthcenter(@Valid HealthCenter healthCenter, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "healthCenter/create";
        }

        healthCenterService.save(healthCenter);
        attr.addFlashAttribute("success", "Centro de saúde adicionado com sucesso.");

        return "redirect:/health-center/list";

    }

    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }


    @ModelAttribute("prefectures")
    public List<Prefecture> getPrefectures(){
        return  prefectureService.findAll();
    }

}
