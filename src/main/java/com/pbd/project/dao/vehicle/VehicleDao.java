package com.pbd.project.dao.vehicle;

import com.pbd.project.domain.User;
import com.pbd.project.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, Long> {
    @Query(
            value = "SELECT * FROM VEHICLES WHERE health_center_id = ?1 and is_available = ?2 and is_active = ?3  ",
            nativeQuery = true
    )
    List<Vehicle> findAvailables(Long idHealthCenter, boolean isAvailable, boolean isActive);
}
