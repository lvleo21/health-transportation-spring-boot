package com.pbd.project.service.prefecture;

import com.pbd.project.dao.prefecture.PrefectureDao;
import com.pbd.project.domain.HealthCenter;
import com.pbd.project.domain.Prefecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PrefectureServiceImpl implements PrefectureService {

    @Autowired
    private PrefectureDao dao;

    @Autowired
    private HttpServletRequest request;


    @Override
    public void save(Prefecture prefecture) {
        dao.save(prefecture);
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Prefecture findById(Long id) {
        return dao.findById(id).get();
    }

    @Override
    public List<Prefecture> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Prefecture> findPrefectureByAddress_City(String city) {
        return dao.findPrefectureByCity(city);
    }

    @Override
    public List<Prefecture> getModelAttribute() {
        String path = request.getRequestURI();

        if (path.contains("/health-centers/update")){
            Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            Long id = Long.parseLong((String) pathVariables.get("id"));
            return Arrays.asList(dao.findPrefectureByHealthCenterId(id));
        }

        return dao.getPrefectureByActiveAndHealthCenterIsNull(true);
    }
}
