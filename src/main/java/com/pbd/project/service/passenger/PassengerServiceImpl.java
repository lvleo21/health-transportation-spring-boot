package com.pbd.project.service.passenger;

import com.pbd.project.dao.passenger.PassengerDao;
import com.pbd.project.domain.Address;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Passenger;
import com.pbd.project.domain.User;
import com.pbd.project.service.address.AddressService;
import com.pbd.project.service.role.RoleService;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

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
    public List<Passenger> findPassengerByHealthCenterAndActive(HealthCenter healthCenter, boolean active) {
        return passengerDao.findPassengerByHealthCenterAndActive(healthCenter, active);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findPassengerByHealthCenter(HealthCenter healthCenter) {
        return passengerDao.findPassengerByHealthCenter(healthCenter);
    }

    @Override
    public void changePassengerStatus(Passenger passenger, boolean active) {
        passenger.setActive(active);
        passengerDao.save(passenger);
    }

    @Override
    public List<Passenger> getModelAttribute() {
        User user = userService.getUserAuthenticated();

        if (user.getStaff()) {
            return this.findAll();
        } else {
            return this.findPassengerByHealthCenter(user.getHealthCenter());
        }
    }

    @Override
    public Page<Passenger> findAll(int currentPage) {
        return passengerDao.findAll(this.getPageable(currentPage));
    }

    @Override
    public Page<Passenger> findPassengerByName(int currentPage, String name) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Passenger> findPassengerByHealthCenter(int currentPage, HealthCenter healthCenter) {
        return passengerDao.findPassengerByHealthCenter(this.getPageable(currentPage), healthCenter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Passenger> findPassengerByHealthCenterAndActive(int currentPage, HealthCenter healthCenter, boolean active) {
        return passengerDao.findPassengerByHealthCenterAndActive(this.getPageable(currentPage), healthCenter, active);
    }

    @Override
    public Page<Passenger> getPassengers(int currentPage, String name) {
        User user = userService.getUserAuthenticated();

        if (user.getStaff()){
            return (name == null) ?
                    this.findAll(currentPage) :
                    this.findPassengerByName(currentPage, name);
        }
        else if(user.getRoles().contains(roleService.findByRole("GESTOR"))){
            return (name.isEmpty()) ?
                    this.findPassengerByHealthCenter(currentPage, user.getHealthCenter()) :
                    this.findPassengerByHealthCenterAndNameContainsIgnoreCase(
                            currentPage,
                            user.getHealthCenter(),
                            name
                    );
        }

        return null;
    }

    @Override
    public Page<Passenger> findPassengerByHealthCenterAndNameContainsIgnoreCase(int currentPage, HealthCenter healthCenter, String name) {
        return passengerDao.findPassengerByHealthCenterAndNameContainsIgnoreCase(
                this.getPageable(currentPage),
                healthCenter,
                name
        );
    }



    @Override
    @Transactional(readOnly = true)
    public Page<Passenger> findPassengerByHealthCenterAndActiveAndNameContainsIgnoreCase(int currentPage, HealthCenter healthCenter, boolean isActive, String name) {
        return passengerDao.findPassengerByHealthCenterAndActiveAndNameContainsIgnoreCase(
                this.getPageable(currentPage),
                healthCenter,
                isActive,
                name
        );
    }


    public Pageable getPageable(int currentPage){
        return PageRequest.of(currentPage, 12, Sort.by("name").ascending());
    }


}
