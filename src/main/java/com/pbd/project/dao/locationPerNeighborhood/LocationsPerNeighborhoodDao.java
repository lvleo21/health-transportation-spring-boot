package com.pbd.project.dao.locationPerNeighborhood;


import com.pbd.project.domain.views.LocationsPerNeighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationsPerNeighborhoodDao extends JpaRepository<LocationsPerNeighborhood, Integer> {

    @Query(value="SELECT * FROM LocationsPerNeighborhood", nativeQuery = true)
    List<LocationsPerNeighborhood> getAll();

    @Query(value="SELECT * FROM LocationsPerNeighborhood WHERE health_center_id = ?1", nativeQuery = true)
    List<LocationsPerNeighborhood> findByHealth_center_id(Integer id);

}
