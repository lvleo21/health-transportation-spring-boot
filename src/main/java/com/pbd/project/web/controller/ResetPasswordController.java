package com.pbd.project.web.controller;


import com.pbd.project.domain.OrderResetPassword;
import com.pbd.project.domain.User;
import com.pbd.project.service.orderResetPassword.OrderResetPasswordService;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Controller
public class ResetPasswordController {

    @Autowired
    private OrderResetPasswordService orderResetPasswordService;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("users/reset-requests")
    public String getOrderResetPasswords(ModelMap model) {

        User user = userService.getUserAuthenticated();
        List<OrderResetPassword> orderResetPasswordList;

        if (user.getStaff()) {
            orderResetPasswordList = orderResetPasswordService.findAll();
        } else {
            orderResetPasswordList = orderResetPasswordService.findByHealthCenters(user.getHealthCenter().getId());
        }

        model.addAttribute("orps", orderResetPasswordList);

        return "user/reset-password/list";

    }

    @GetMapping("users/reset-requests/{id}/rejected")
    public String rejectedPassword(@PathVariable("id") Long id, RedirectAttributes attr) {
        OrderResetPassword orderResetPassword = orderResetPasswordService.findById(id);
        orderResetPassword.setWasRejected(true);
        orderResetPasswordService.update(orderResetPassword);

        attr.addFlashAttribute("success", "Pedido rejeitado com sucesso !");
        return "redirect:/users/reset-requests";
    }

    @GetMapping("users/reset-requests/{id}/reset")
    public String resetPassword(@PathVariable("id") Long id, RedirectAttributes attr) {
        try {
            OrderResetPassword orderResetPassword = orderResetPasswordService.findById(id);

            //! Gero uma nova senha e já atualizo o usuário com a nova senha;
            String newPassword = userService.resetPassword(orderResetPassword.getUserWhoRequested());

            //! chamo o sendmail enviando o usuário e a nova senha;
            this.sendMail(orderResetPassword.getUserWhoRequested(), newPassword);

            //! Atualizo o pedido como concluído e digo quem concluíu;
            orderResetPasswordService.update(orderResetPassword);

        } catch (Exception e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/users/reset-requests";
        }

        attr.addFlashAttribute("success", "Senha resetada com sucesso !");
        return "redirect:/users/reset-requests";
    }


    public void sendMail(User user, String newPassword) throws MessagingException {
        //! Preparando o texto a ser enviado
        StringBuilder builder = new StringBuilder();
        builder.append("<p>Caro <b>" + user.getName() + "</b>,</p>");
        builder.append("<h2>A senha da sua conta do Health Transportation foi alterada.</h2>");
        builder.append("<h4>Sua nova senha é: " + newPassword + "</h4>");
        builder.append("<p>Se você não atualizou sua conta, informe-nos imediatamente. Envie um e-mail para <b>pbd.healthtransportation@gmail.com</b>.</p>");
        builder.append("<p>Obrigada,<br>A Equipe de Contas do Health Transportation.</p> ");

        //! Enviando o email;
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);
        helper.setTo(user.getEmail());
        helper.setSubject("A senha da sua conta do Health Transportation foi redefinida");
        helper.setText(builder.toString(), true);
        mailSender.send(mail);
    }


}
