package com.pbd.project.web.validation;

import com.pbd.project.domain.User;
import com.pbd.project.dto.Employee;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmpĺoyeeValidator implements Validator {


    private final UserService userService;


    public EmpĺoyeeValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> c) {
        return Employee.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors e) {

        if(e.hasErrors()){
            return;
        }

        Employee employeeDto = (Employee) obj;

        User user = userService.findById(employeeDto.getId());


        User validateUsername = userService.findByUsername(employeeDto.getUsername());
        User validateEmail = userService.findByEmail(employeeDto.getEmail());
        User validateEnrollment = userService.findByEnrollment(employeeDto.getEnrollment(), userService.getUserAuthenticated().getHealthCenter());


        if( validateUsername != null && validateUsername.getId() != user.getId()){
            e.rejectValue("username", "Unique.username");
        }

        if( validateEmail != null && validateEmail.getId() != user.getId()){
            e.rejectValue("email", "Unique.email");
        }

        if( validateEnrollment != null && validateEnrollment.getId() != user.getId()){
            e.rejectValue("enrollment", "Unique.enrollment");
        }

        if (employeeDto.getName().isEmpty()) {
            e.rejectValue("name", "NotEmpty.name");
        }
        if (employeeDto.getEnrollment().isEmpty()) {
            e.rejectValue("enrollment", "NotEmpty.enrollment");
        }

        if (employeeDto.getUsername().isEmpty()) {
            e.rejectValue("username", "NotEmpty.username");
        }

        if (employeeDto.getEmail().isEmpty()) {
            e.rejectValue("email", "NotEmpty.email");
        }

    }
}
