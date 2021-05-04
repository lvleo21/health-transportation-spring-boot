package com.pbd.project.web.conversor;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.service.healthCenter.HealthCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToHealthCenter implements Converter<String, HealthCenter> {

    @Autowired
    private HealthCenterService healthCenterService;

    @Override
    public HealthCenter convert(String text) {
        return (!text.isEmpty()) ? healthCenterService.findById(Long.valueOf(text)) : null;
    }
}
