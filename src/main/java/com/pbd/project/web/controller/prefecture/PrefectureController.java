package com.pbd.project.web.controller.prefecture;

import com.pbd.project.domain.Prefecture;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.prefecture.PrefectureService;
import com.pbd.project.service.user.UserService;
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
@RequestMapping("/prefecture")
public class PrefectureController {

    @Autowired
    private PrefectureService prefectureService;

    @Autowired
    private UserService userService;



    @GetMapping("/list")
    public String getPrefectures(ModelMap modelMap, @RequestParam("city") Optional<String> city) {

        List<Prefecture> prefectures = null;
        String nameQueryParam = city.orElse(null);

        if (nameQueryParam != null){
            prefectures = prefectureService.
                    findPrefectureByAddress_City(nameQueryParam);
        } else {
             prefectures = prefectureService.findAll();
        }

        modelMap.addAttribute("city", nameQueryParam);
        modelMap.addAttribute("prefectures", prefectures);
        modelMap.addAttribute("queryIsEmpty", prefectures.size() == 0 ? true : false);
        modelMap.addAttribute("isSearch", nameQueryParam == null ? false : true);

        return "prefecture/list";
    }

    @GetMapping("/register")
    public String viewRegisterPrefecture(Prefecture prefecture) {
        return "prefecture/create";
    }

    @PostMapping("/register/save")
    public String savePrefecture(@Valid Prefecture prefecture, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {

            return "prefecture/create";
        }

        prefectureService.save(prefecture);

        attr.addFlashAttribute("success", "Prefeitura adicionada com sucesso.");
        return "redirect:/prefecture/list";

    }

    @GetMapping("/delete/{id}")
    public String deletePrefecture(@PathVariable("id") Long id, RedirectAttributes attr) {

        if(prefectureService.findById(id).getHealthCenter() != null){
            attr.addFlashAttribute("error",
                    "Erro ao remover a prefeitura. O mesmo, possuí um centro de saúde vinculado.");
            return "redirect:/prefecture/list";
        }

        prefectureService.delete(id);
        attr.addFlashAttribute("success", "Prefeitura removida com sucesso.");
        return "redirect:/prefecture/list";

    }


    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable("id") Long id, ModelMap model) {

        Prefecture prefecture = prefectureService.findById(id);

        model.addAttribute("prefecture", prefecture);

        return "prefecture/create";
    }

    @PostMapping("/update/save")
    public String update(@Valid Prefecture prefecture, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "prefecture/create";
        }

        prefectureService.save(prefecture);
        attr.addFlashAttribute("success", "Prefeitura editada com sucesso.");

        return "redirect:/prefecture/list";

    }

    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }


}
