package com.pbd.project.web.conversor;

import com.pbd.project.domain.Driver;
import com.pbd.project.domain.Vehicle;
import com.pbd.project.service.driver.DriverService;
import com.pbd.project.service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToVehicle implements Converter<String, Vehicle> {

    @Autowired
    private VehicleService vehicleService;

    public Vehicle convert(String text) {
        return (!text.isEmpty()) ? vehicleService.findById(Long.valueOf(text)) : null;
    }
}