package com.danit.dto;

import com.danit.models.BaseEntity;
import com.danit.utils.CustomDateTimeDeserializer;
import com.danit.utils.CustomDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"creationDate", "lastModifiedBy", "lastModifiedDate", "createdBy"},
    allowGetters = true)
public class BaseDto implements BaseEntity {

  @JsonView({Views.Ids.class, Views.Short.class})
  private Long id;

  @JsonView(Views.Extended.class)
  private String createdBy;

  @JsonView(Views.Extended.class)
  @JsonSerialize(using = CustomDateTimeSerializer.class)
  @JsonDeserialize(using = CustomDateTimeDeserializer.class)
  private Date creationDate;

  @JsonView(Views.Extended.class)
  private String lastModifiedBy;

  @JsonView(Views.Extended.class)
  @JsonSerialize(using = CustomDateTimeSerializer.class)
  @JsonDeserialize(using = CustomDateTimeDeserializer.class)
  private Date lastModifiedDate;

}
