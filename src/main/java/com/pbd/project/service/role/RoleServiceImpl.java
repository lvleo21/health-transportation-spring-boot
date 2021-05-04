package com.pbd.project.service.role;

import com.pbd.project.dao.role.RoleDao;
import com.pbd.project.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao rolaDao;

    @Override
    public Role findByRole(String role) {
        return rolaDao.findByRole(role);
    }

    @Override
    public List<Role> specificRoles() {
        return rolaDao.specificRoles();
    }

    @Override
    @Transactional(readOnly = false)
    public Role findById(Long id) {
        return rolaDao.findById(id).get();
    }

    @Override
    public List<Role> findAll() {
        return rolaDao.findAll();
    }
}
