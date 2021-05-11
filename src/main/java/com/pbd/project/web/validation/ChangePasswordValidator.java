package com.pbd.project.web.validation;

import com.pbd.project.domain.ChangePassword;
import com.pbd.project.domain.User;
import com.pbd.project.service.user.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class ChangePasswordValidator implements Validator {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;


    @Override
    public boolean supports(Class<?> c) {
        return ChangePassword.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ChangePassword changePassword = (ChangePassword) obj;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String password = changePassword.getNewPassword();


        if(!bCryptPasswordEncoder.matches(changePassword.getOldPassword(), user.getPassword())){
            errors.rejectValue("oldPassword", "ChangePassword.invalidMatch");
        }

        if(changePassword.getNewPassword().isEmpty()){
            errors.rejectValue("newPassword", "ChangePassword.empty.password");
        }

        if(changePassword.getOldPassword().isEmpty()){
            errors.rejectValue("oldPassword", "ChangePassword.empty.password");
        }

        if (!(password.length() >= 6 && password.length() <= 11)) {
            errors.rejectValue("newPassword", "User.length.error");
        }

        // Ao menos um número
        if(password.matches("^[^\\d]+$")){
            errors.rejectValue("newPassword", "User.regex.number.error");
        }

        // Ao menos uma letra maiúsucla
        if(password.matches("^[^A-Z]+$")){
            errors.rejectValue("newPassword", "User.regex.uppercase.error");
        }
        // Ao menos uma letra minúscula
        if(password.matches("^[^a-z]+$")){
            errors.rejectValue("newPassword", "User.regex.lowercase.error");
        }

        // não pode conter caracteres especiais
        if(!password.matches("^[^\\W]*$")){
            errors.rejectValue("newPassword", "User.regex.special.error");
        }


    }
}
