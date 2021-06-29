package com.pbd.project.service.log;


import com.pbd.project.dao.log.LogDao;
import com.pbd.project.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LogServiceImpl implements LogService{

    @Autowired
    private LogDao logDao;

    @Transactional(readOnly = true)
    public Page<Log> findAll(int currentPage) {
        return logDao.findAll(this.getPageable(currentPage));
    }

    public Pageable getPageable(int currentPage){
        return PageRequest.of(currentPage, 12, Sort.by("id").descending());
    }
}
