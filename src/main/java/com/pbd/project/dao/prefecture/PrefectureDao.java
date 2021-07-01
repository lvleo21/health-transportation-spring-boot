package com.pbd.project.dao.prefecture;

import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Prefecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrefectureDao extends JpaRepository<Prefecture, Long> {
    List<Prefecture> getPrefectureByHealthCenterIsNull();
    @Query(value = "select p.* from prefectures p, adresses a \n" +
            "where p.address_id = a.id and lower(a.city) LIKE lower(concat('%', ?1,'%'));", nativeQuery = true)
    List<Prefecture> findPrefectureByCity(String City);
    Prefecture findPrefectureByHealthCenterId(Long id);
}
