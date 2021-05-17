package com.pbd.project.web.validation;

import com.pbd.project.domain.Passenger;
import com.pbd.project.service.passenger.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;

@Component
public class PassengerValidator implements Validator {

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean supports(Class<?> c) {
        return Passenger.class.equals(c);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Passenger passenger = (Passenger) obj;
        String path = request.getRequestURI();

        String updatePath = "/passengers/update";

        if(passenger.getRg() != null){
            Passenger findByRg  = passengerService.findPassengerByRg(passenger.getRg());


            if( findByRg != null && passenger.getId() != findByRg.getId()){
                errors.rejectValue("rg", "Unique.rg");
            }
        }

        if(passenger.getSus() != null){

            Passenger findBySus  = passengerService.findPassengerBySus(passenger.getSus());


            if( findBySus != null && passenger.getId() != findBySus.getId()){
                errors.rejectValue("sus", "Unique.sus");
            }
        }

    }
}
