package com.pbd.project.dao.role;

import com.pbd.project.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByRole(String role);
    @Query(value = "SELECT * from roles where role != 'ADMIN'", nativeQuery = true)
    List<Role> specificRoles();

}
