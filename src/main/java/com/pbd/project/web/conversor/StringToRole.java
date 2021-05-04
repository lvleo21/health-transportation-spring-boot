package com.pbd.project.web.conversor;

import com.pbd.project.dao.role.RoleDao;
import com.pbd.project.domain.Role;
import com.pbd.project.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRole implements Converter<String, Role> {

    @Autowired
    private RoleService roleService;

    @Override
    public Role convert(String text) {
        return (!text.isEmpty()) ? roleService.findById(Long.valueOf(text)) : null;
    }
}
