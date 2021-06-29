package com.pbd.project.dao.log;

import com.pbd.project.domain.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Repository
public interface LogDao extends JpaRepository<Log, Long> {
    Page<Log> findAll(Pageable pageable);
    Page<Log> findByCreatedAt(Pageable pageable, LocalDate createdAt);
}
