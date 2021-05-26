package com.pbd.project.web.controller.healthCenter;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Prefecture;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.prefecture.PrefectureService;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/health-centers")
public class HealthCenterController {


    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private PrefectureService prefectureService;

    @GetMapping("")
    public String healthCenterListView(ModelMap model, @RequestParam("city") Optional<String> city){

        String cityParam = city.orElse("");
        List<HealthCenter> healthCenters;

        if (cityParam != ""){
            healthCenters = healthCenterService.findHealthCentersByCity(cityParam);
            model.addAttribute("isSearch", true);
            model.addAttribute("city", cityParam);
        } else{
            healthCenters = healthCenterService.findAll();
            model.addAttribute("isSearch", false);
        }

        System.out.println(healthCenters);

        model.addAttribute("healthCenters", healthCenters);
        model.addAttribute("querIsEmpty", healthCenters.isEmpty());

        return "healthCenter/list";
    }

    @GetMapping("/create")
    public String healthCenterCreateView(HealthCenter healthCenter, ModelMap model){
        model.addAttribute("createView", true);
        return "healthCenter/create";
    }

    @PostMapping("/create/save")
    public String healthCenterCreateSave(@Valid HealthCenter healthCenter, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "healthCenter/create";
        }

        healthCenterService.save(healthCenter);
        attr.addFlashAttribute("success", "Centro de saúde adicionado com sucesso.");

        return "redirect:/health-centers";
    }

    @GetMapping("/update/{id}")
    public String healthCenterUpdateView(@PathVariable("id") Long id, ModelMap model){
        HealthCenter healthCenter = healthCenterService.findById(id);
        model.addAttribute("healthCenter", healthCenter);

        return "healthCenter/create";
    }

    @PostMapping("/update/save")
    public String healthCenterUpdateSave(@Valid HealthCenter healthCenter, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "healthCenter/create";
        }

        healthCenterService.save(healthCenter);
        attr.addFlashAttribute("success", "Centro de saúde editado com sucesso.");
        return "redirect:/health-centers";
    }


    @GetMapping("/delete/{id}")
    public String healthCenterDelete(@PathVariable("id") Long id, RedirectAttributes attr) {
        healthCenterService.deleteById(id);
        attr.addFlashAttribute("success", "Centro de saúde removido com sucesso.");
        return "redirect:/health-centers";
    }



    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }


    @ModelAttribute("prefectures")
    public List<Prefecture> getPrefectures(){
        return prefectureService.getModelAttribute();
    }

}
