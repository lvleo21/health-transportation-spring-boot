package com.pbd.project.web.controller.travel;

import com.pbd.project.domain.*;
import com.pbd.project.domain.enums.PassengerCategory;
import com.pbd.project.domain.enums.PassengerTransition;
import com.pbd.project.domain.enums.TravelStatus;
import com.pbd.project.domain.enums.UF;
import com.pbd.project.service.driver.DriverService;
import com.pbd.project.service.healthCenter.HealthCenterService;
import com.pbd.project.service.location.LocationService;
import com.pbd.project.service.passenger.PassengerService;
import com.pbd.project.service.travel.TravelService;
import com.pbd.project.service.user.UserService;
import com.pbd.project.service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private UserService userService;

    @Autowired
    private HealthCenterService healthCenterService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("")
    public String travelsListView(ModelMap model) {

        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            model.addAttribute("travels", travelService.findAll());
        } else {
            model.addAttribute("travels", travelService.findByHealthCenter(user.getHealthCenter()));
        }

        return "travel/list";
    }

    @GetMapping("/create")
    public String travelsCreateView(Travel travel, ModelMap model) {
        model.addAttribute("createView", true);
        return "travel/createOrUpdate";
    }

    @PostMapping("/create/save")
    public String createTravel(@Valid Travel travel, BindingResult result, RedirectAttributes attr, ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("createView", true);
            return "travel/createOrUpdate";
        }

        travelService.save(travel);
        attr.addFlashAttribute("success", "Viagem adicionada com sucesso.");

        return "redirect:/travels";
    }

    @GetMapping("/update/{id}")
    public String travelUpdateView(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {

        Travel travel = travelService.findById(id);
        User user = userService.getUserAuthenticated();

        if (!user.getStaff()) {
            if (!user.getHealthCenter().getId().equals(travel.getHealthCenter().getId())) {
                attr.addFlashAttribute("error", "Você não tem permissões para editar este veículo.");
                return "redirect:/travels";
            }
        }

        model.addAttribute("travel", travel);
        model.addAttribute("createView", false);
        return "travel/createOrUpdate";
    }

    @PostMapping("/update/{id}/save")
    public String travelUpdateSave(@Valid Travel travel, BindingResult result, RedirectAttributes attr, ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("createView", false);
            return "travel/createOrUpdate";
        }

        travelService.update(travel);

        attr.addFlashAttribute("success", "Viagem atualizada com sucesso.");
        return "redirect:/travels";
    }

    @GetMapping("/delete/{id}")
    public String deleteTravel(@PathVariable("id") Long id, RedirectAttributes attr) {

        Travel travel = travelService.findById(id);


        if (travel.getRegisteredPassengers() == 0) {
            travelService.delete(travel);
            attr.addFlashAttribute("success", "Viagem deletada com sucesso.");
        } else {
            attr.addFlashAttribute("error", "Está viagem possue passageiros cadastrados.");
        }

        return "redirect:/travels";
    }

    @GetMapping("/{id}/locations")
    public String getAllLocation(@PathVariable("id") Long id, ModelMap model) {

        Travel travel = travelService.findById(id);

        model.addAttribute("travel", travel);
        model.addAttribute("locations", locationService.findByTravel(travel));

        return "location/list";
    }

    @GetMapping("/{id}/locations/create")
    public String locationCreateView(@PathVariable("id") Long id, Location location, ModelMap model) {
        Travel travel = travelService.findById(id);
        model.addAttribute("travel", travel);
        model.addAttribute("createView", true);

        return "location/createOrUpdate";
    }

    @PostMapping("/{id}/locations/create/save")
    public String createLocation(@PathVariable("id") Long id, @Valid Location location, BindingResult result, RedirectAttributes attr, ModelMap model) {

        if (result.hasErrors()) {
            Travel travel = travelService.findById(id);
            model.addAttribute("travel", travel);
            model.addAttribute("createView", true);
            return "location/createOrUpdate";
        }

        locationService.save(location);
        attr.addFlashAttribute("success", "Passageiro(a) adicionado(a) com sucesso.");

        return "redirect:/travels/" + id + "/locations";
    }

    @GetMapping("/{idTravel}/locations/{idLocation}/update")
    public String locationUpdateView(@PathVariable("idTravel") Long idTravel, @PathVariable("idLocation") Long idLocation, ModelMap model, RedirectAttributes attr) {

        Travel travel = travelService.findById(idTravel);
        Location location = locationService.findById(idLocation);
        model.addAttribute("travel", travel);
        model.addAttribute("createView", false);
        model.addAttribute("location", location);

        return "location/createOrUpdate";
    }

    @PostMapping("/{idTravel}/locations/{idLocation}/update/save")
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

    @GetMapping("{idTravel}/locations/{idLocation}/delete")
    public String deleteLocation(@PathVariable("idTravel") Long idTravel,
                                 @PathVariable("idLocation") Long idLocation,
                                 RedirectAttributes attr) {


        locationService.delete(locationService.findById(idLocation));

        attr.addFlashAttribute("success", "Locação deletada com sucesso.");

        return "redirect:/travels/" + idTravel + "/locations";

    }


    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters() {
        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            return healthCenterService.findAll();
        } else {
            List<HealthCenter> healthCenters = new ArrayList<>();
            HealthCenter healthCenter = healthCenterService.findById(user.getHealthCenter().getId());
            healthCenters.add(healthCenter);

            return healthCenters;
        }
    }

    @ModelAttribute("passengers")
    public List<Passenger> passengers() {
        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            return passengerService.findAll();
        } else {
            return passengerService.findPassengerByHealthCenter(user.getHealthCenter());
        }
    }

    @ModelAttribute("drivers")
    public List<Driver> drivers() {
        User user = userService.getUserAuthenticated();
        String path = request.getRequestURI();

        if (user.getStaff()) {
            return driverService.findAll();
        } else {
            List<Driver> drivers = driverService.findAvailable(user.getHealthCenter().getId(), true, true);

            if (path.contains("/travels/update/")) {
                drivers.add(getTravel().getDriver());
            }

            return drivers;
        }
    }

    @ModelAttribute("vehicles")
    public List<Vehicle> vehicles() {
        User user = userService.getUserAuthenticated();
        String path = request.getRequestURI();

        if (user.getStaff()) {
            return vehicleService.findAll();
        } else {
            List<Vehicle> vehicles = vehicleService.findAvailable(user.getHealthCenter().getId(), true, true);

            if (path.contains("/travels/update/")) {
                vehicles.add(getTravel().getVehicle());
            }

            return vehicles;
        }
    }

    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }

    @ModelAttribute("passengerCategories")
    public PassengerCategory[] getPassengerCategories() {
        return PassengerCategory.values();
    }

    @ModelAttribute("passengerTransitions")
    public PassengerTransition[] getPassengerTransitions() {
        return PassengerTransition.values();
    }

    @ModelAttribute("travelStatus")
    public TravelStatus[] getTravelStatus() {
        return TravelStatus.values();
    }


    public Travel getTravel() {
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long id = Long.parseLong((String) pathVariables.get("id"));
        return travelService.findById(id);
    }

}
