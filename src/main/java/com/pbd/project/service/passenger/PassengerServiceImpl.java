package com.pbd.project.service.passenger;

import com.pbd.project.dao.passenger.PassengerDao;
import com.pbd.project.domain.Address;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;
import com.pbd.project.service.address.AddressService;
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

    @Autowired
    private AddressService addressService;

    @Override
    public void save(Passenger passenger) {
        Address adr = addressService.findAddress(passenger.getAddress());

        if (adr != null) {
            passenger.setAddress(adr);
        } else{
            passenger.setAddress(addressService.save(passenger.getAddress()));
        }

        passengerDao.save(passenger);
    }

    @Override
    public void update(Passenger passenger) {

        Passenger tempPassenger = this.findPassengerByAddressAndId(passenger.getAddress(), passenger.getId());

        //! Diferente de null é pq sofreu alteração no endereço
        if(tempPassenger != null){
            Address adr = addressService.findAddress(passenger.getAddress());

            if (adr != null) {
                passenger.setAddress(adr);
            } else{
                passenger.setAddress(addressService.save(passenger.getAddress()));
            }
        }

        passengerDao.save(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public Passenger findById(Long id) {
        return passengerDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Passenger findPassengerBySus(String sus) {
        return passengerDao.findPassengerBySus(sus);
    }

    @Override
    @Transactional(readOnly = true)
    public Passenger findPassengerByRg(String rg) {
        return passengerDao.findPassengerByRg(rg);
    }


    @Override
    @Transactional(readOnly = true)
    public Passenger findPassengerByAddressAndId(Address address, Long id) {
        return passengerDao.findPassengerByAddressAndId(address, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findAll() {
        return passengerDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findByHealthCenter(HealthCenter healthCenter) {
        return passengerDao.findPassengerByHealthCenterAndActive(healthCenter, true);
    }
}
