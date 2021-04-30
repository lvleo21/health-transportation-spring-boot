package com.pbd.project.dao.prefecture;

import com.pbd.project.domain.Prefecture;

import java.util.List;

public interface PrefectureDao {
    void save(Prefecture prefecture);
    void update(Prefecture prefecture);
    void delete(Long id);
    Prefecture findById(Long id);
    List<Prefecture> findAll();
}
