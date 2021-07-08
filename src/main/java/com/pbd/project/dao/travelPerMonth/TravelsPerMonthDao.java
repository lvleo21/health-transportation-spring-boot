package com.pbd.project.dao.travelPerMonth;

import com.pbd.project.domain.views.TravelsPerMonthViews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TravelsPerMonthDao extends JpaRepository<TravelsPerMonthViews, Integer> {

    @Query(value="SELECT * FROM TravelsPerMonthByCurrentYear", nativeQuery = true)
    List<TravelsPerMonthViews> findAll();

    @Query(value="SELECT * FROM TravelsPerMonthByCurrentYear WHERE health_center_id = ?1", nativeQuery = true)
    List<TravelsPerMonthViews> findAByHealthCenterId(Integer id);
}
