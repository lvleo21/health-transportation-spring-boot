package com.pbd.project.service.healthCenter;

import com.pbd.project.domain.HealthCenter;

import java.util.List;

public interface HealthCenterService {
    void save(HealthCenter healthCenter);
    void update(HealthCenter healthCenter);
    void delete(Long id);
    HealthCenter findById(Long id);
    List<HealthCenter> findAll();
}
