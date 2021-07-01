package com.pbd.project.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> extends AbstractEntity<Long>{

    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastModifiedDate;

    @PreRemove
    public void preRemove(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        this.setLastModifiedBy((U) auth.getName());
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}