package com.pbd.project.web.validation;

import com.pbd.project.dto.UserEmployeeDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmpĺoyeeValidator implements Validator{
    @Override
    public boolean supports(Class<?> c) {
        return UserEmployeeDto.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors e) {

        UserEmployeeDto employeeDto = (UserEmployeeDto) obj;

        if(employeeDto.getName().isEmpty()){
            e.rejectValue("name", "NotEmpty.name");
        }
        if(employeeDto.getEnrollment().isEmpty()){
            e.rejectValue("enrollment", "NotEmpty.enrollment");
        }

        if(employeeDto.getUsername().isEmpty()){
            e.rejectValue("username", "NotEmpty.username");
        }

        if(employeeDto.getEmail().isEmpty()){
            e.rejectValue("email", "NotEmpty.email");
        }




    }
}
