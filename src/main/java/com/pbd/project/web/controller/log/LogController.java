package com.pbd.project.web.controller.log;

import com.pbd.project.domain.Log;
import com.pbd.project.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;


@Controller
@RequestMapping("/logs")
public class LogController {
    
    @Autowired
    private LogService logService;
    
    @GetMapping("")
    public String getLogs(ModelMap model, @RequestParam("page") Optional<Integer> page){
        int currentPage = page.orElse(0);
        Page<Log> logs = logService.findAll(currentPage);
        model.addAttribute("logs", logs);
        //model.addAttribute("passengerName", passengerName);
        model.addAttribute("isSearch", false);
        model.addAttribute("queryIsEmpty", logs.getTotalElements() == 0 ? true : false);
        model.addAttribute("dateToday", LocalDate.now());

        return "log/list";
    }



}
