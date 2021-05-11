package com.pbd.project.web.validation;

import com.pbd.project.domain.User;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserValidator implements Validator {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> c) {
        return User.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        String path = request.getRequestURI();
        String updatePath = "/users/update/save";
        String createPath = "/users/create/save";

        User user = (User) obj;

        if(path.equals(updatePath)){
            User userDTO = userService.findByUsername(user.getUsername());

            user.setId(userDTO.getId());
            user.setPassword(userDTO.getPassword());
            user.setCreatedAt(userDTO.getCreatedAt());
            user.setStaff(userDTO.getStaff());

            if(!user.getStaff()){
                user.setRoles(userDTO.getRoles());
                user.setHealthCenter(userDTO.getHealthCenter());
            }

        }

        System.out.println(user.toString());

        if (!user.getStaff()) {
            if (user.getHealthCenter() == null) {
                errors.rejectValue("healthCenter", "User.healthCenter.empty");
            }

            if (user.getEnrollment().isEmpty()) {
                errors.rejectValue("enrollment", "User.enrollment.empty");
            }

            if (user.getRoles().isEmpty()) {
                errors.rejectValue("roles", "User.role.empty");
            }
        }


        if(path.equals(createPath)){

            String password = user.getPassword();

            if (!(password.length() >= 6 && password.length() <= 11)) {
                errors.rejectValue("password", "User.length.error");
            }

            // Ao menos um número
            if (password.matches("^[^\\d]+$")) {
                errors.rejectValue("password", "User.regex.number.error");
            }

            // Ao menos uma letra maiúsucla
            if (password.matches("^[^A-Z]+$")) {
                errors.rejectValue("password", "User.regex.uppercase.error");
            }
            // Ao menos uma letra minúscula
            if (password.matches("^[^a-z]+$")) {
                errors.rejectValue("password", "User.regex.lowercase.error");
            }

            // não pode conter caracteres especiais
            if (!password.matches("^[^\\W]*$")) {
                errors.rejectValue("password", "User.regex.special.error");
            }
        }
    }
}
