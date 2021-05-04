package com.pbd.project.service.healthCenter;


import com.pbd.project.dao.healthCenter.HealthCenterDao;
import com.pbd.project.domain.HealthCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HealthCenterServiceImpl implements HealthCenterService {

    @Autowired
    private HealthCenterDao dao;


    @Override
    public void save(HealthCenter healthCenter) {
        dao.save(healthCenter);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public HealthCenter findById(Long id) {
        return dao.getOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthCenter> findAll() {
        return dao.findAll();
    }
}
