package com.pbd.project.web.controller.log;

import com.pbd.project.domain.Log;
import com.pbd.project.service.log.LogService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Controller
@RequestMapping("/logs")
public class LogController {
    
    @Autowired
    private LogService logService;
    
    @GetMapping("")
    public String getLogs(ModelMap model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("date") Optional<String> date){
        int currentPage = page.orElse(0);
        String tempSelectedDate = date.orElse("");
        LocalDate selectedDate = tempSelectedDate == "" ? null : LocalDate.parse(tempSelectedDate, DateTimeFormatter.ISO_DATE);

        Page<Log> logs = null;
        if (selectedDate == null) {
            logs = logService.findAll(currentPage);
        } else {
            logs = logService.findCreatedAt(currentPage, selectedDate);
        }

        model.addAttribute("logs", logs);
        model.addAttribute("isSearch", selectedDate == null ? false : true);
        model.addAttribute("queryIsEmpty", logs.getTotalElements() == 0 ? true : false);
        model.addAttribute("dateToday", LocalDate.now());
        model.addAttribute("dateSelected", selectedDate);

        return "log/list";
    }



}
