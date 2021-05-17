package com.pbd.project.service.address;

import com.pbd.project.domain.Address;

public interface AddressService {
    Address save(Address address);
    Address findAddress(Address address);
}
