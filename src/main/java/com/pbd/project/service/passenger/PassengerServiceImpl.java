package com.pbd.project.service.passenger;

import com.pbd.project.dao.passenger.PassengerDao;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService{

    @Autowired
    private PassengerDao passengerDao;

    @Override
    public void save(Passenger passenger) {
        passengerDao.save(passenger);
    }

    @Override
    public void update(Passenger passenger) {
        this.save(passenger);
    }

    @Override
    public Passenger findById(Long id) {
        return passengerDao.findById(id).get();
    }

    @Override
    public List<Passenger> findAll() {
        return passengerDao.findAll(Sort.by(Sort.Direction.ASC));
    }

    @Override
    public List<Passenger> findByHealthCenter(HealthCenter healthCenter) {
        return passengerDao.findPassengerByHealthCenterAndActive(healthCenter, true);
    }
}
