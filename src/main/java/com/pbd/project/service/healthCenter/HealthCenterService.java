package com.pbd.project.service.healthCenter;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Prefecture;

import java.util.List;
import java.util.Optional;

public interface HealthCenterService {
    void save(HealthCenter healthCenter);
    void deleteById(Long id);
    HealthCenter findById(Long id);
    List<HealthCenter> findAll();
    List<HealthCenter> getModelAttribute();
    List<HealthCenter> findHealthCentersByCity(String city);
}
