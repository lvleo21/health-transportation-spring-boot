package com.pbd.project.web.controller.vehicle;

import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.User;
import com.pbd.project.domain.Vehicle;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.user.UserService;
import com.pbd.project.service.vehicle.VehicleService;
import com.pbd.project.web.validation.VehicleValidator;
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
import java.util.Optional;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private VehicleValidator vehicleValidator;

    @InitBinder()
    public void initialBinder(WebDataBinder binder) {
        binder.setValidator(this.vehicleValidator);
    }

    @GetMapping("")
    public String vehiclesListView(ModelMap model, @RequestParam("name") Optional<String> name){

        User user = userService.getUserAuthenticated();
        List<Vehicle> vehicles = null;
        String nameQueryParam = name.orElse(null);

        if(user.getStaff()){
            vehicles = (nameQueryParam == null)
                    ? vehicleService.findAll()
                    : vehicleService.findVehicleByName(nameQueryParam);
        } else {
            vehicles = (nameQueryParam == null)
                    ? vehicleService.findByHealthcenter(user.getHealthCenter())
                    : vehicleService.findVehicleByHealthCenterAndName(
                            user.getHealthCenter(), nameQueryParam);

        }


        model.addAttribute("name", nameQueryParam);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("queryIsEmpty", vehicles.size() == 0 ? true : false);
        model.addAttribute("isSearch", nameQueryParam == null ? false : true);

        return "vehicle/list";
    }

    @GetMapping("/create")
    public String vehiclesCreateView(Vehicle vehicle, ModelMap model){
        model.addAttribute("createView", true);
        return "vehicle/createOrUpdate";
    }

    @PostMapping("/create/save")
    public String createVehicle(@Valid Vehicle vehicle, BindingResult result, RedirectAttributes attr, ModelMap model){

        if(result.hasErrors()){
            model.addAttribute("createView", true);
            return "vehicle/createOrUpdate";
        }

        vehicleService.save(vehicle);
        attr.addFlashAttribute("success", "<b>" + vehicle.getName() +"</b> adicionado com sucesso.");

        return  "redirect:/vehicles";

    }

    @GetMapping("/update/{plaque}")
    public String vehicleUpdateView(@PathVariable("plaque") String plaque, ModelMap model, RedirectAttributes attr){

        Vehicle vehicle = vehicleService.findByPlaque(plaque);
        User user = userService.getUserAuthenticated();


        if(!user.getStaff()){
            if (!user.getHealthCenter().getId().equals(vehicle.getHealthCenter().getId())){
                attr.addFlashAttribute("error", "Você não tem permissões para editar este veículo.");
                return  "redirect:/vehicles";
            }
        }

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("createView", false);
        return "vehicle/createOrUpdate";
    }

    @PostMapping("/update/{plaque}/save")
    public String vehicleUpdateSave(@Valid Vehicle vehicle, BindingResult result, RedirectAttributes attr, ModelMap model){

        if (result.hasErrors()){
            model.addAttribute("createView", false);
            return "vehicle/createOrUpdate";
        }

        vehicleService.save(vehicle);

        attr.addFlashAttribute("success", "<b>" + vehicle.getName() +"</b> atualizado com sucesso.");
        return  "redirect:/vehicles";
    }

    @GetMapping("delete/{id}")
    public String deleteVehicle(@PathVariable("id") Long id, RedirectAttributes attr){

        vehicleService.delete(id);
        attr.addFlashAttribute("success", "Veículo deletado com sucesso.");
        return  "redirect:/vehicles";
    }



    @GetMapping("/{id}/change-status")
    public String changeVehicleStatus(@PathVariable("id") Long id, RedirectAttributes attr){
        Vehicle vehicle = vehicleService.findById(id);

        if (this.hasPermission(vehicle.getHealthCenter().getId())) {
            vehicleService.changeActive(vehicle);
            attr.addFlashAttribute("success", "Veículo <b>"+ vehicle.getName() + "</b> modificado(a) com sucesso.");
        } else {
            attr.addFlashAttribute("error", "Você não tem permissões para modificar este veículo.");
        }

        return "redirect:/vehicles";
    }

    public boolean hasPermission(Long idDriverHealthCenter) {
        User user = userService.getUserAuthenticated();
        return (user.getStaff() || (user.getHealthCenter().getId().equals(idDriverHealthCenter))) ? true : false;
    }

    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters() {
        return healthCenterService.getModelAttribute();
    }

}
