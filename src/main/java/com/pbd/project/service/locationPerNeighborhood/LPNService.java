package com.pbd.project.service.locationPerNeighborhood;

import com.pbd.project.domain.views.LocationsPerNeighborhood;

import java.util.List;

public interface LPNService {

    List<LocationsPerNeighborhood> findAll();
    List<LocationsPerNeighborhood> findByHealthCenter(Integer id);

}
