package com.pbd.project.service.exportLocation;

import com.pbd.project.dao.exportLocation.ExportLocationsDao;
import com.pbd.project.domain.views.ExportLocationViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ExportLocationsServiceImpl implements ExportLocationsService{

    @Autowired
    private ExportLocationsDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<ExportLocationViews> getAll() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExportLocationViews> findByTravelId(Long id) {
        return dao.findByTravel_id(id);
    }
}
