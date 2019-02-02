package com.danit.models.service;

import com.danit.utils.deserializers.CustomDateTimeDeserializer;
import com.danit.utils.serializers.CustomDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "creationDate",
    allowGetters = true)
@Data
public class Tab {

  @CreatedDate
  @Temporal(TIMESTAMP)
  @JsonSerialize(using = CustomDateTimeSerializer.class)
  @JsonDeserialize(using = CustomDateTimeDeserializer.class)
  protected Date creationDate;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  private Long userId;
  private String baseEntityName;
  private Long baseEntityId;
  @Transient
  private Boolean busy;

  @Transient
  private String message;

}
