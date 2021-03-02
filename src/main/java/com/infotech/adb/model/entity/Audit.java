package com.infotech.adb.model.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by mudassir on 19/01/2017.
 */
@MappedSuperclass
public abstract class Audit {
    @CreatedDate
    @Column(name = "CREATED_DATE")
    protected Date createdDate;
    @CreatedBy
    @Column(name = "CREATED_BY_ID")
    protected String createdById;
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    protected Date lastModifiedDate;
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY_ID")
    protected String lastModifiedById;
    @Column(name = "IS_DELETED")
    protected Boolean isDeleted = false;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedById() {
        return lastModifiedById;
    }

    public void setLastModifiedById(String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
