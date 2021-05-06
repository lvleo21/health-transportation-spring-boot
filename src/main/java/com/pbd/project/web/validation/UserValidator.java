package com.pbd.project.web.validation;

import com.pbd.project.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> c) {
        return User.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        String password = user.getPassword();

        if (user.getHealthCenter() == null){
            errors.rejectValue("healthCenter", "User.healthCenter.empty");
        }

        if (user.getEnrollment().isEmpty()){
            errors.rejectValue("enrollment", "User.enrollment.empty");
        }

        if (user.getRoles().isEmpty()){
            errors.rejectValue("roles", "User.role.empty");
        }

        if (!(password.length() >= 6 && password.length() <= 11)) {
            errors.rejectValue("password", "User.length.error");
        }

        // Ao menos um número
        if(password.matches("^[^\\d]+$")){
            errors.rejectValue("password", "User.regex.number.error");
        }

        // Ao menos uma letra maiúsucla
        if(password.matches("^[^A-Z]+$")){
            errors.rejectValue("password", "User.regex.uppercase.error");
        }
        // Ao menos uma letra minúscula
        if(password.matches("^[^a-z]+$")){
            errors.rejectValue("password", "User.regex.lowercase.error");
        }

        // não pode conter caracteres especiais
        if(!password.matches("^[^\\W]*$")){
            errors.rejectValue("password", "User.regex.special.error");
        }


    }
}
