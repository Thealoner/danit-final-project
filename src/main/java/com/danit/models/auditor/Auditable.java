package com.danit.models.auditor;


import com.danit.utils.CustomDateTimeDeserializer;
import com.danit.utils.CustomDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@JsonIgnoreProperties(value = {"creationDate","lastModifiedBy","lastModifiedDate","createdBy"},
allowGetters = true)
@Data
public abstract class Auditable {

  @CreatedBy
  protected String createdBy;

  @CreatedDate
  @Temporal(TIMESTAMP)
  @JsonSerialize(using = CustomDateTimeSerializer.class)
  @JsonDeserialize(using = CustomDateTimeDeserializer.class)
  protected Date creationDate;

  @LastModifiedBy
  protected String lastModifiedBy;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  @JsonSerialize(using = CustomDateTimeSerializer.class)
  @JsonDeserialize(using = CustomDateTimeDeserializer.class)
  protected Date lastModifiedDate;

}