package com.pbd.project.dao.location;

import com.pbd.project.domain.Location;
import com.pbd.project.domain.Passenger;
import com.pbd.project.domain.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationDao extends JpaRepository<Location, Long> {
    List<Location> findByTravel(Travel travel);
    @Query(value = "select * from locations l, travels t where l.passenger_id = ?1 and t.status = ?2 and t.id = l.travel_id;", nativeQuery = true)
    List<Location> findLocationByPassengerAndTravelStatus(Long idPassenger, String travelStatus);
}
