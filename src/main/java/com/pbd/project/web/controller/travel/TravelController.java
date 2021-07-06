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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public String travelsListView(ModelMap model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("date") Optional<String> date) {



        int currentPage = page.orElse(0);
        String tempSelectedDate = date.orElse("");
        LocalDate selectedDate = tempSelectedDate == "" ? null : LocalDate.parse(tempSelectedDate, DateTimeFormatter.ISO_DATE);


        User user = userService.getUserAuthenticated();
        Page<Travel> travels = null;

        if (selectedDate != null) {
            travels = (user.getStaff())
                    ? this.travelService.findTravelByDepartureDate(currentPage, selectedDate)
                    : this.travelService.findTravelByHealthCenterAndDepartureDate(currentPage, user.getHealthCenter(),selectedDate);

        } else {
            travels = (user.getStaff())
                    ? this.travelService.findAll(currentPage)
                    : this.travelService.findTravelByHealthCenter(currentPage, user.getHealthCenter());
        }


        model.addAttribute("travels", travels);
        model.addAttribute("isSearch", selectedDate == null ? false : true);
        model.addAttribute("queryIsEmpty", travels.getTotalElements() == 0 ? true : false);
        model.addAttribute("dateToday", LocalDate.now());
        model.addAttribute("date", selectedDate);


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
    public String travelUpdateSave(@Valid Travel travel, BindingResult result,
                                   RedirectAttributes attr, ModelMap model,
                                   @PathVariable("id") Long id) {

        if (result.hasErrors()) {
            model.addAttribute("createView", false);
            return "travel/createOrUpdate";
        }
        if (!travelService.update(travel)) {

            int qntPassageiros = travelService.findById(id).getQntPassengers();

            model.addAttribute("error", "Veículo selecionado não comporta a atual " +
                    "quantidade de passageiros cadastrados na viagem (Quantidade atual:</b> " + qntPassageiros + ")");

            model.addAttribute("travel", travel);
            model.addAttribute("createView", false);

            return "travel/createOrUpdate";
        } else {
            attr.addFlashAttribute("success", "Viagem atualizada com sucesso.");
        }

        return "redirect:/travels";
    }

    @GetMapping("/delete/{id}")
    public String deleteTravel(@PathVariable("id") Long id, RedirectAttributes attr) {

        Travel travel = travelService.findById(id);


        if (travel.getLocations().isEmpty()) {
            travelService.delete(travel);
            attr.addFlashAttribute("success", "Viagem deletada com sucesso.");
        } else {
            attr.addFlashAttribute("error", "Está viagem possui passageiros cadastrados.");
        }

        return "redirect:/travels";
    }

    @ModelAttribute("healthCenters")
    public List<HealthCenter> healthCenters() {
        return healthCenterService.getModelAttribute();
    }

    @ModelAttribute("drivers")
    public List<Driver> drivers() {
        User user = userService.getUserAuthenticated();
        String path = request.getRequestURI();

        if (user.getStaff()) {
            return driverService.findDriverByActiveAndAvailable(true, true);
        } else {
            List<Driver> drivers = driverService.getAllDriversByAvailableAndActive(user.getHealthCenter().getId(), true, true);

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
            return vehicleService.findVehicleByActiveAndAvailable(true, true);
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
