package com.example.temanbelajar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"created_at", "updated_at"},
    allowGetters = true
)
@Getter
@Setter
public abstract class AuditModel implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @LastModifiedDate
    private Date updated_at;

    // public Date getCreatedAt(){
    //     return createdAt;
    // }

    // public void setCreatedAt(Date createdAt) {
    //     this.createdAt = createdAt;
    // }

    // public Date getUpdatedAt() {
    //     return updatedAt;
    // }

    // public void setUpdatedAt(Date updatedAt){
    //     this.updatedAt = updatedAt;
    // }

    @PrePersist
    public void onPrePersist() {
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updated_at = new Date();
    }

    @PreRemove
    public void onPreRemove() {
        this.updated_at = new Date();
    }

    

    
}