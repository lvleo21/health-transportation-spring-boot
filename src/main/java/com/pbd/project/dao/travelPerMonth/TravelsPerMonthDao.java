package com.pbd.project.dao.travelPerMonth;

import com.pbd.project.domain.views.TravelsPerMonthViews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelsPerMonthDao extends JpaRepository<TravelsPerMonthViews, Integer> {

    @Query(value="SELECT * FROM TravelsPerMonthByCurrentYear", nativeQuery = true)
    List<TravelsPerMonthViews> findAll();


}
