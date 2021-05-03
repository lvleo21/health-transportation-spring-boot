package com.pbd.project.dao.healthCenter;

import com.pbd.project.domain.HealthCenter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthCenterDao {
    void save(HealthCenter healthCenter);
    void update(HealthCenter healthCenter);
    void delete(Long id);
    HealthCenter findById(Long id);
    List<HealthCenter> findAll();



}
