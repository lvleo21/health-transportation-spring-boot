package com.pbd.project.service.healthCenter;


import com.pbd.project.dao.healthCenter.HealthCenterDao;
import com.pbd.project.domain.HealthCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HealthCenterServiceImpl implements HealthCenterService {

    @Autowired
    private HealthCenterDao dao;

    @Override
    public void save(HealthCenter healthCenter) {
        dao.save(healthCenter);
    }

    @Override
    public void update(HealthCenter healthCenter) {
        dao.update(healthCenter);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public HealthCenter findById(Long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthCenter> findAll() {
        return dao.findAll();
    }
}
