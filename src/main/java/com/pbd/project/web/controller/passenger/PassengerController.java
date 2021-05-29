package com.pbd.project.web.controller.passenger;

import com.pbd.project.domain.*;
import com.pbd.project.domain.enums.Gender;
import com.pbd.project.domain.enums.TravelStatus;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.location.LocationService;
import com.pbd.project.service.passenger.PassengerService;
import com.pbd.project.service.role.RoleService;
import com.pbd.project.service.user.UserService;
import com.pbd.project.web.validation.PassengerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private UserService userService;

    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private PassengerValidator passengerValidator;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LocationService locationService;



//    @InitBinder
//    public void userInitBinder(WebDataBinder binder) {
//        binder.setValidator(this.passengerValidator);
//    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(this.passengerValidator);
    }


    @GetMapping("")
    public String passengersListView(ModelMap model) {

        User user = userService.getUserAuthenticated();


        Passenger passenger = passengerService.findPassengerByRg("4.123.123");

        if (user.getStaff()) {
            model.addAttribute("passengers", passengerService.findAll());
        } else {

            if (user.getRoles().contains(roleService.findByRole("GESTOR"))){
                model.addAttribute("passengers", passengerService.findPassengerByHealthCenter(user.getHealthCenter()));
            } else{
                model.addAttribute("passengers", passengerService.findPassengerByHealthCenterAndActive(user.getHealthCenter(), true));
            }
        }

        return "passenger/list";

    }

    @GetMapping("/create")
    public String passengersCreateView(Passenger passenger, ModelMap model) {
        model.addAttribute("createView", true);
        return "passenger/createOrUpdate";
    }

    @PostMapping("/create/save")
    public String createPassenger(@Valid Passenger passenger, BindingResult result, ModelMap model, RedirectAttributes attr){


        System.out.println("RESULT => " + result.hasErrors());

        if (result.hasErrors()) {
            model.addAttribute("createView", true);
            return "passenger/createOrUpdate";
        }

        passengerService.save(passenger);

        attr.addFlashAttribute("success", "<b>"+ passenger.getName() + "</b> adicionado com sucesso.");

        return "redirect:/passengers";
    }

    @GetMapping("/update/{rg}")
    public String travelUpdateView(@PathVariable("rg") String rg, ModelMap model, RedirectAttributes attr) {
        Passenger passenger = passengerService.findPassengerByRg(rg);
        User user = userService.getUserAuthenticated();

        if (!user.getStaff()) {
            if (!user.getHealthCenter().getId().equals(passenger.getHealthCenter().getId())) {
                attr.addFlashAttribute("error", "Você não tem permissões para editar este veículo.");
                return "redirect:/passengers";
            }
        }

        model.addAttribute("passenger", passenger);
        model.addAttribute("createView", false);
        return "passenger/createOrUpdate";
    }

    @PostMapping("/update/{rg}/save")
    public String travelUpdateSave(@Valid Passenger passenger, BindingResult result, RedirectAttributes attr, ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("createView", false);
            return "passenger/createOrUpdate";
        }

        passengerService.update(passenger);

        attr.addFlashAttribute("success", "<b>"+passenger.getName()+"</b> atualizado(a) com sucesso.");
        return "redirect:/passengers";
    }

    @GetMapping("/{rg}/deactivate")
    public String deactivatePassenger(@PathVariable("rg") String rg, RedirectAttributes attr) {

        Passenger passenger = passengerService.findPassengerByRg(rg);


        if(passenger != null){

            if (locationService.findLocationByPassengerAndTravelStatus(passenger.getId(), TravelStatus.AGUARDANDO.getName()).isEmpty()){
                passengerService.changePassengerStatus(passenger, false);
                attr.addFlashAttribute("success", "<b>"+passenger.getName()+"</b> desativado(a) com sucesso.");
            } else{
                attr.addFlashAttribute("error", "Este passageiro está em uma viagem ativa, tente novamente mais tarde.");
            }
        } else{
            attr.addFlashAttribute("error", "Erro ao tentar ativar o passageiro, tente novamente.");
        }



        //! Tenho que verificar se não esta em nenhuma viagem ativa;

        return "redirect:/passengers";
    }

    @GetMapping("/{rg}/activate")
    public String activatePassenger(@PathVariable("rg") String rg, RedirectAttributes attr) {
        Passenger passenger = passengerService.findPassengerByRg(rg);


        //! faz um método para isso
        User user = userService.getUserAuthenticated();
        if (!user.getStaff()) {
            if (!user.getHealthCenter().getId().equals(passenger.getHealthCenter().getId())) {
                attr.addFlashAttribute("error", "Você não tem permissões para editar este veículo.");
                return "redirect:/passengers";
            }
        }

        if(passenger != null){
            passengerService.changePassengerStatus(passenger, true);
            attr.addFlashAttribute("success", "<b>"+passenger.getName()+"</b> ativado(a) com sucesso.");
        } else{
            attr.addFlashAttribute("error", "Erro ao tentar ativar o passageiro, tente novamente.");
        }

        return "redirect:/passengers";
    }




    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }

    @ModelAttribute("genres")
    public Gender[] getGenres() {
        return Gender.values();
    }

    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters() {
        return healthCenterService.getModelAttribute();
    }
}
