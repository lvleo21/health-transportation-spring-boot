package com.pbd.project.service.locationPerNeighborhood;

import com.pbd.project.dao.locationPerNeighborhood.LocationsPerNeighborhoodDao;
import com.pbd.project.domain.views.LocationsPerNeighborhood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LPNServiceImpl implements LPNService {

    @Autowired
    private LocationsPerNeighborhoodDao dao;


    @Override
    public List<LocationsPerNeighborhood> findAll() {
        return dao.getAll();
    }

    @Override
    public List<LocationsPerNeighborhood> findByHealthCenter(Integer id) {
        return dao.findByHealth_center_id(id);
    }
}
