package com.pbd.project.domain;

import com.pbd.project.configuration.SpringSecurityAuditorAware;
import com.pbd.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@Table(name="LOG")
public class Log extends AbstractEntity<Long> {


    @Column(name="created_by")
    private String createdBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "created_at", columnDefinition = "DATE")
    private LocalDate createdAt;

    @Column(columnDefinition = "TEXT")
    private String operation;

    @Column(columnDefinition = "TEXT", name="before_operation")
    private String beforeOperation;

    @Column(columnDefinition = "TEXT", name="after_operation")
    private String afterOperation;

    @Column(name = "table_name")
    private String tableName;


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getBeforeOperation() {
        return beforeOperation;
    }

    public void setBeforeOperation(String beforeOperation) {
        this.beforeOperation = beforeOperation;
    }

    public String getAfterOperation() {
        return afterOperation;
    }

    public void setAfterOperation(String afterOperation) {
        this.afterOperation = afterOperation;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
