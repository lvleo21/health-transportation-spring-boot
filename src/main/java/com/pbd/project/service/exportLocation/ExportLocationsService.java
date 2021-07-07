package com.pbd.project.service.exportLocation;


import com.pbd.project.domain.views.ExportLocationViews;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ExportLocationsService {
    List<ExportLocationViews> getAll();
    List<ExportLocationViews> findByTravelId(Long id);

}
