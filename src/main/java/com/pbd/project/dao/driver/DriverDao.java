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

    //! PARA GESTOR
    Page<Driver> findDriversByHealthCenter(Pageable pageable, HealthCenter healthCenter);
    //! PARA GESTOR
    Page<Driver> findDriversByHealthCenterAndNameContainsIgnoreCase(Pageable pageable, HealthCenter healthCenter, String name);
    //! PARA OPERADOR
    Page<Driver> findDriversByHealthCenterAndNameAndActive(Pageable pageable, HealthCenter healthCenter, String name, boolean active);
    //! PARA ADMIN
    Page<Driver> findDriversByNameContainsIgnoreCase(Pageable pageable, String name);


    //! For get all

    //! For search
}
