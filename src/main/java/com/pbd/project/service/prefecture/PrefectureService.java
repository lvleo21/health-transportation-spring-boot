package com.pbd.project.service.prefecture;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Prefecture;

import java.util.List;

public interface PrefectureService {
    void save(Prefecture cargo);
    void delete(Long id);
    Prefecture findById(Long id);
    List<Prefecture> findAll();

    List<Prefecture> findPrefectureByAddress_City(String city);

    List<Prefecture> getModelAttribute();
}
