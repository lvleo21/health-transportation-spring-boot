package com.pbd.project.web.conversor;


import com.pbd.project.domain.Prefecture;
import com.pbd.project.service.prefecture.PrefectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPrefecture implements Converter<String, Prefecture> {

    @Autowired
    private PrefectureService prefectureService;

    @Override
    public Prefecture convert(String text) {
        return (!text.isEmpty()) ? prefectureService.findById(Long.valueOf(text)) : null;
    }
}
