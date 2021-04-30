package com.pbd.project.dao;

import com.pbd.project.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
