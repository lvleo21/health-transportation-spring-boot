package com.pbd.project.web.validation;

import com.pbd.project.domain.Vehicle;
import com.pbd.project.service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class VehicleValidator implements Validator {

    @Autowired
    private VehicleService vehicleService;

    @Override
    public boolean supports(Class<?> c) {
        return Vehicle.class.isAssignableFrom(c);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        Vehicle vehicle = (Vehicle) obj;

        vehicle.setPlaque(vehicle.getPlaque().toUpperCase(Locale.ROOT));

        Vehicle searchByPlaque = vehicleService.findByPlaque(vehicle.getPlaque());


        if(searchByPlaque != null && vehicle.getId() != searchByPlaque.getId()){
            errors.rejectValue("plaque", "Unique.plaque");
        }


    }
}
