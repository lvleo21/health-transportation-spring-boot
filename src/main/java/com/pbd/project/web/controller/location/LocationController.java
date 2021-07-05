package com.pbd.project.web.controller.location;

import com.pbd.project.domain.Location;
import com.pbd.project.domain.Passenger;
import com.pbd.project.domain.Travel;
import com.pbd.project.domain.enums.PassengerCategory;
import com.pbd.project.domain.enums.PassengerTransition;
import com.pbd.project.domain.enums.TravelStatus;
import com.pbd.project.service.location.LocationService;
import com.pbd.project.service.passenger.PassengerService;
import com.pbd.project.service.travel.TravelService;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.HandlerMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/travels/{idTravel}")
public class LocationController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PassengerService passengerService;


    @Autowired
    private HttpServletRequest request;


    @GetMapping("/locations")
    public String locationListView(@PathVariable("idTravel") Long idTravel,
                                   ModelMap model,
                                   @RequestParam("name") Optional<String> name){

        String nameQueryParam = name.orElse(null);
        Travel travel  = travelService.findById(idTravel);
        List<Location> locations;

        locations = (nameQueryParam == null)
                ? travel.getLocations()
                : locationService.findLocationByPassengerName(idTravel, nameQueryParam);


        model.addAttribute("queryIsEmpty", locations.size() == 0 ? true : false);
        model.addAttribute("isSearch", nameQueryParam == null ? false : true);
        model.addAttribute("name", nameQueryParam);
        model.addAttribute("travel", travel);
        model.addAttribute("locations", locations);

        boolean permissionForChangeTravelStatus = false;
        String nextStatus = "";



        switch (travel.getStatus()){
            case AGUARDANDO:
                permissionForChangeTravelStatus = true;
                nextStatus = "EM TRÂNSITO";
                break;
            case EM_TRANSITO:
                permissionForChangeTravelStatus = true;
                nextStatus = "CONCLUÍDO";
                break;
        }
        boolean canEdit = (travel.getStatus() == TravelStatus.AGUARDANDO) ? true : false;

        model.addAttribute("canEdit", canEdit);
        model.addAttribute("permissionForChangeTravelStatus", permissionForChangeTravelStatus);
        model.addAttribute("nextStatus", nextStatus);

        return "location/list";
    }

    @GetMapping("/locations/change-status")
    public String changeTravelStatus(@PathVariable("idTravel") Long idTravel, RedirectAttributes attr){
        if (this.travelService.changeTravelStatus(idTravel)){
            attr.addFlashAttribute("success", "Status da viagem alterado com sucesso.");
        } else{
            attr.addFlashAttribute("error", "Ocorreu um erro ao tentar alterar o status da viagem, tente novamente.");
        }

        return "redirect:/travels/" + idTravel + "/locations";
    }

    @GetMapping("/locations/export")
    public String getTeste(@PathVariable("idTravel") Long idTravel, ModelMap model){
        Travel travel = travelService.findById(idTravel);
        model.addAttribute("travel", travel);
        model.addAttribute("prefecture", travel.getHealthCenter().getPrefecture());
        return "htmlToPdf";
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
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String path = request.getRequestURI();

        Long id = null;
        if (pathVariables.get("idLocation") != null){
            id = Long.parseLong((String) pathVariables.get("idLocation"));
        }



        if (path.contains("/locations/" + id +"/update")) {
            List<Passenger> passengers = new ArrayList<>();
            passengers.add(locationService.findById(id).getPassenger());
            return passengers;
        } else {
            return passengerService.getModelAttribute();
        }
    }


}
