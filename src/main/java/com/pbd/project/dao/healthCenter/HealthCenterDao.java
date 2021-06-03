package com.pbd.project.dao.healthCenter;

import com.pbd.project.domain.HealthCenter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthCenterDao extends JpaRepository<HealthCenter, Long>{
    void deleteById(Long id);

    @Query(
            value = "SELECT hc.* " +
                    "from health_centers hc, prefectures p, adresses a " +
                    "WHERE UPPER(a.city) LIKE UPPER(CONCAT('%',?1,'%')) and hc.prefecture_id = p.id and p.address_id = a.id",
            nativeQuery = true
    )
    List<HealthCenter> findHealthCentersByCity(String city);
}
