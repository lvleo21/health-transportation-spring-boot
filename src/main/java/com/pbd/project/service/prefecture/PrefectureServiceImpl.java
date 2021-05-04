package com.pbd.project.service.prefecture;

import com.pbd.project.dao.prefecture.PrefectureDao;
import com.pbd.project.domain.Prefecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PrefectureServiceImpl implements PrefectureService {

    @Autowired
    private PrefectureDao dao;

    @Override
    public void save(Prefecture prefecture) {
        dao.save(prefecture);
    }

    @Override
    public void update(Prefecture prefecture) {
        dao.update(prefecture);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Prefecture findById(Long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prefecture> findAll() {
        return dao.findAll();
    }

}
