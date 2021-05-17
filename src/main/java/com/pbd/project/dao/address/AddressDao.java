package com.pbd.project.dao.address;

import com.pbd.project.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<Address, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM adresses WHERE " +
            "city = ?1 and " +
            "state = ?2 and " +
            "country = ?3 and " +
            "neighborhood = ?4 and " +
            "number = ?5 and " +
            "public_place = ?6"
    )
    Address findAddress(String city, String state, String country, String neighborhood, Integer number, String public_place);
}
