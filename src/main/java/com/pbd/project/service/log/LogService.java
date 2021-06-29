package com.pbd.project.service.log;


import com.pbd.project.domain.Log;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


public interface LogService {
    Page<Log> findAll(int currentPage);
    Page<Log> findCreatedAt(int currentPage, LocalDate createdAt);
}
