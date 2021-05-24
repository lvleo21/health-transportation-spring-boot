package com.pbd.project.web.controller.location;

import com.pbd.project.domain.Location;
import com.pbd.project.domain.Passenger;
import com.pbd.project.domain.Travel;
import com.pbd.project.domain.User;
import com.pbd.project.domain.enums.PassengerCategory;
import com.pbd.project.domain.enums.PassengerTransition;
import com.pbd.project.service.location.LocationService;
import com.pbd.project.service.passenger.PassengerService;
import com.pbd.project.service.travel.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/travels/{idTravel}")
public class LocationController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PassengerService passengerService;

    @GetMapping("/locations")
    public String locationListView(@PathVariable("idTravel") Long idTravel, ModelMap model){

        Travel travel = travelService.findById(idTravel);
        model.addAttribute("travel", travel);
        model.addAttribute("locations", travel.getLocations());

        return "location/list";
    }

    @GetMapping("/locations/create")
    public String locationCreateView(@PathVariable("idTravel") Long idTravel, Location location, ModelMap model) {

        Travel travel = travelService.findById(idTravel);
        model.addAttribute("travel", travel);
        model.addAttribute("createView", true);

        return "location/createOrUpdate";
    }

    @PostMapping("/locations/create/save")
    public String locationCreateSave(@PathVariable("idTravel") Long idTravel, @Valid Location location, BindingResult result, RedirectAttributes attr, ModelMap model) {

        if (result.hasErrors()) {
            Travel travel = travelService.findById(idTravel);
            model.addAttribute("travel", travel);
            model.addAttribute("createView", true);
            return "location/createOrUpdate";
        }

        locationService.save(location);
        attr.addFlashAttribute("success", "Passageiro(a) adicionado(a) com sucesso.");

        return "redirect:/travels/" + idTravel + "/locations";
    }

    @GetMapping("/locations/{idLocation}/update")
    public String locationUpdateView(@PathVariable("idTravel") Long idTravel, @PathVariable("idLocation") Long idLocation, ModelMap model, RedirectAttributes attr) {

        Travel travel = travelService.findById(idTravel);
        Location location = locationService.findById(idLocation);
        model.addAttribute("travel", travel);
        model.addAttribute("location", location);
        model.addAttribute("createView", false);

        return "location/createOrUpdate";
    }

    @PostMapping("/locations/{idLocation}/update/save")
    public String locationUpdateSave(@PathVariable("idTravel") Long idTravel,
                                     @PathVariable("idLocation") Long idLocation,
                                     @Valid Location location,
                                     BindingResult result,
                                     RedirectAttributes attr,
                                     ModelMap model) {

        if (result.hasErrors()) {
            Travel travel = travelService.findById(idTravel);
            model.addAttribute("travel", travel);
            model.addAttribute("createView", false);
            model.addAttribute("location", location);

            return "location/createOrUpdate";
        }

        locationService.save(location);
        attr.addFlashAttribute("success", "Passageiro(a) editado(a) com sucesso.");

        return "redirect:/travels/" + idTravel + "/locations";
    }

    @GetMapping("/locations/{idLocation}/delete")
    public String deleteLocation(@PathVariable("idTravel") Long idTravel,
                                 @PathVariable("idLocation") Long idLocation,
                                 RedirectAttributes attr) {


        locationService.delete(locationService.findById(idLocation));

        attr.addFlashAttribute("success", "Locação deletada com sucesso.");

        return "redirect:/travels/" + idTravel + "/locations";

    }

    @ModelAttribute("passengerCategories")
    public PassengerCategory[] getPassengerCategories() {
        return PassengerCategory.values();
    }

    @ModelAttribute("passengerTransitions")
    public PassengerTransition[] getPassengerTransitions() {
        return PassengerTransition.values();
    }

    @ModelAttribute("passengers")
    public List<Passenger> passengers() {
        return passengerService.getModelAttribute();
    }

}
