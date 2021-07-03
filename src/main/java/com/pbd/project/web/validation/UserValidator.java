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
        return User.class.isAssignableFrom(c);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        String path = request.getRequestURI();
        String updatePath = "/users/update/";
        String createPath = "/users/create/";

        User user = (User) obj;


        if (path.contains(updatePath)) {
            System.out.println("USER =>" + user.toString());

            User tempUser = userService.findById(user.getId());
            user.setPassword(tempUser.getPassword());
            user.setCreatedAt(tempUser.getCreatedAt());

            if (tempUser.getStaff()) {
                user.setRoles(tempUser.getRoles());
            }

            System.out.println("USER => " + user.toString());
        }

        //! Serve apenas para quando for criar um usuário;
        if (path.contains(createPath)) {

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

        //! Serve apenas para GESTOR e OPERADOR
        if (!user.getStaff()) {

            User enrollmentIsExist = userService.findByEnrollment(user.getEnrollment(), user.getHealthCenter());
            User emailIsExist = userService.findByEmail(user.getEmail());
            User usernameIsExist = userService.findByUsername(user.getUsername());


            if (user.getHealthCenter() == null) {
                errors.rejectValue("healthCenter", "User.healthCenter.empty");
                errors.rejectValue("enrollment", "Error.enrollment");
            }

            if (user.getEnrollment().isEmpty()) {
                errors.rejectValue("enrollment", "User.enrollment.empty");
            }

            if (enrollmentIsExist != null && enrollmentIsExist.getId() != user.getId()) {
                errors.rejectValue("enrollment", "Unique.enrollment");
            }

            if (emailIsExist != null && enrollmentIsExist.getId() != user.getId()) {
                errors.rejectValue("email", "Unique.email");
            }

            if (usernameIsExist != null && enrollmentIsExist.getId() != user.getId()) {
                errors.rejectValue("username", "Unique.username");
            }
        }

        //! Parte comum a todos os usuários
        if (user.getRoles().isEmpty()) {
            errors.rejectValue("roles", "User.role.empty");
        }

        if (user.getEmail().isEmpty()) {
            errors.rejectValue("email", "NotEmpty.email");
        }

        if (user.getUsername().isEmpty()) {
            errors.rejectValue("username", "NotEmpty.username");
        }

        if (user.getName().isEmpty()) {
            errors.rejectValue("name", "NotEmpty.name");
        }

    }
}
