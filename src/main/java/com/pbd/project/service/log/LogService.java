package com.pbd.project.service.log;


import com.pbd.project.domain.Log;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface LogService {
    Page<Log> findAll(int currentPage);
}
