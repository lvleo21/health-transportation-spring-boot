package com.pbd.project.service.healthCenter;


import com.pbd.project.dao.healthCenter.HealthCenterDao;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.User;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HealthCenterServiceImpl implements HealthCenterService {

    @Autowired
    private HealthCenterDao dao;

    @Autowired
    private UserService userService;

    @Override
    public void save(HealthCenter healthCenter) {
        dao.save(healthCenter);
    }

    public boolean deleteById(Long id) {
        HealthCenter healthCenter = this.findById(id);

        if (healthCenter.getDrivers().isEmpty()
                && healthCenter.getPassengers().isEmpty()
                && healthCenter.getVehicles().isEmpty()
                && healthCenter.getUsers().isEmpty()
                && healthCenter.getTravels().isEmpty()){

            dao.deleteById(id);

            return true;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public HealthCenter findById(Long id) {
        return dao.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthCenter> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthCenter> getModelAttribute() {
        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            return this.findAll();
        } else {
            HealthCenter healthCenter = this.findById(user.getHealthCenter().getId());
            return Arrays.asList(healthCenter);
        }
    }

    @Override
    public List<HealthCenter> findHealthCentersByCity(String city) {
        return dao.findHealthCentersByCity(city);
    }


}
