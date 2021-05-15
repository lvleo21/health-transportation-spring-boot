package com.pbd.project.web.conversor;

import com.pbd.project.domain.Driver;
import com.pbd.project.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDriver implements Converter<String, Driver> {

    @Autowired
    private DriverService driverService;

    public Driver convert(String text) {
        return (!text.isEmpty()) ? driverService.findById(Long.valueOf(text)) : null;
    }
}
