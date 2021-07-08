package com.pbd.project.service.travelPerMonth;


import com.pbd.project.dao.travelPerMonth.TravelsPerMonthDao;
import com.pbd.project.domain.views.TravelsPerMonthViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TravelsPerMonthServiceImpl implements TravelsPerMonthService{

    @Autowired
    private TravelsPerMonthDao dao;

    @Override
    public List<TravelsPerMonthViews> findAll() {
        return dao.findAll();
    }

    @Override
    public List<TravelsPerMonthViews> findByHealthCenterId(Integer id) {
        return dao.findAByHealthCenterId(id);
    }


}
