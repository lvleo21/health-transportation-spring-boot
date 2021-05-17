package com.pbd.project.web.validation;

import com.pbd.project.domain.Passenger;
import com.pbd.project.service.passenger.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PassengerValidator implements Validator {

    @Autowired
    private PassengerService passengerService;

    @Override
    public boolean supports(Class<?> c) {
        return Passenger.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Passenger passenger = (Passenger) obj;


        if(passengerService.findPassengerByRg(passenger.getRg()) != null){
            errors.rejectValue("rg", "Unique.rg");
        }

        if(passengerService.findPassengerBySus(passenger.getSus()) != null){
            errors.rejectValue("sus", "Unique.sus");
        }

    }
}
