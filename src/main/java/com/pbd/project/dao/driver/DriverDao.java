package com.pbd.project.dao.driver;

import com.pbd.project.domain.Driver;
import com.pbd.project.domain.HealthCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverDao extends JpaRepository<Driver, Long> {
    @Query(
            value = "SELECT * FROM DRIVERS WHERE health_center_id = ?1 and is_available = ?2 and is_active = ?3  ",
            nativeQuery = true
    )
    List<Driver> findAvailables(Long idHealthCenter, boolean isAvailable, boolean isActive);
    List<Driver> findDriverByHealthCenter (HealthCenter healthCenter);
    List<Driver> findAllByOrderByNameAsc();

    Page<Driver> findDriversByHealthCenter(Pageable pageable, HealthCenter healthCenter);
    Page<Driver> findDriversByHealthCenterAndNameAndActive(Pageable pageable, HealthCenter healthCenter, String name);
    Page<Driver> findDriversByHealthCenterAndName(Pageable pageable, HealthCenter healthCenter, String name);
    Page<Driver> findDriversByName(Pageable pageable, String name);
}
