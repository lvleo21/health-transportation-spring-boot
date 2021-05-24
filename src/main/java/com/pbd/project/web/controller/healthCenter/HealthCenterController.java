package com.pbd.project.web.controller.healthCenter;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Prefecture;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.prefecture.PrefectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/health-centers")
public class HealthCenterController {


    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private PrefectureService prefectureService;


    @GetMapping("/")
    public String healthCenterListView(ModelMap model){
        model.addAttribute("healthCenters", healthCenterService.findAll());
        return "healthCenter/list";
    }

    @GetMapping("/create")
    public String healthCenterCreateView(HealthCenter healthCenter){ return "healthCenter/create"; }

    @PostMapping("/create/save")
    public String healthCenterCreateSave(@Valid HealthCenter healthCenter, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "healthCenter/create";
        }

        healthCenterService.save(healthCenter);
        attr.addFlashAttribute("success", "Centro de saúde adicionado com sucesso.");

        return "redirect:/health-center/list";

    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable("id") Long id, ModelMap model){
        HealthCenter healthCenter = healthCenterService.findById(id);
        model.addAttribute("healthCenter", healthCenter);

        return "healthCenter/create";
    }

    @PostMapping("/update/save")
    public String update(@Valid HealthCenter healthCenter, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "healthCenter/create";
        }

        healthCenterService.save(healthCenter);
        attr.addFlashAttribute("success", "Centro de saúde editado com sucesso.");
        return "redirect:/health-center/list";

    }


    @GetMapping("/delete/{id}")
    public String deleteHealthCenter(@PathVariable("id") Long id, RedirectAttributes attr) {
        healthCenterService.deleteById(id);
        attr.addFlashAttribute("success", "Centro de saúde removido com sucesso.");
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
