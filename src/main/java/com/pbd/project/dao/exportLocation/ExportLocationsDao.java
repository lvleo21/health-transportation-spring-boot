package com.pbd.project.dao.exportLocation;

import com.pbd.project.domain.views.ExportLocationViews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExportLocationsDao extends JpaRepository<ExportLocationViews, Long> {

    @Query(value="SELECT * from ExportLocationsView", nativeQuery = true)
    List<ExportLocationViews> findAll();

    @Query(value="SELECT * FROM ExportLocationsView WHERE travel_id = ?1", nativeQuery = true)
    List<ExportLocationViews> findByTravel_id(Long id);
}
