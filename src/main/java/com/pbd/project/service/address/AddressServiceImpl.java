package com.pbd.project.service.address;

import com.pbd.project.dao.address.AddressDao;
import com.pbd.project.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDao dao;

    @Override
    public Address save(Address address) {
        Address adr = dao.saveAndFlush(address);
        return  adr;
    }

    @Override
    @Transactional(readOnly = true)
    public Address findAddress(Address address) {
        return dao.findAddress(
                address.getCity(),
                address.getState().getInitials(),
                address.getCountry(),
                address.getNeighborhood(),
                address.getNumber(),
                address.getPublicPlace()
        );
    }
}
