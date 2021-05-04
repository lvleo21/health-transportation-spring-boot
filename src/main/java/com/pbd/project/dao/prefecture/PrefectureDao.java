package com.pbd.project.dao.prefecture;

import com.pbd.project.domain.Prefecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrefectureDao extends JpaRepository<Prefecture, Long> {
}
