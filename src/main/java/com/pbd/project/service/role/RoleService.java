package com.pbd.project.service.role;

import com.pbd.project.domain.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleService {
    Role findByRole(String role);
    List<Role> specificRoles();
    Role findById(Long id);
    List<Role> findAll();
}
