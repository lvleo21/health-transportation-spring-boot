package com.pbd.project.dao.healthCenter;

import com.pbd.project.domain.HealthCenter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthCenterDao extends JpaRepository<HealthCenter, Long>{
    void deleteById(Long id);
}
