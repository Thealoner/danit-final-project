package com.danit.models.auditor;


import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable {

  @CreatedBy
  protected String createdBy;

  @CreatedDate
  @Temporal(TIMESTAMP)
  protected Date creationDate;

  @LastModifiedBy
  protected String lastModifiedBy;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  protected Date lastModifiedDate;

}