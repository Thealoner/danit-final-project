package com.danit.dto;

import com.danit.utils.CustomDateDeserializer;
import com.danit.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {

  @JsonView(Views.Extended.class)
  private String createdBy;

  @JsonView(Views.Extended.class)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date creationDate;

  @JsonView(Views.Extended.class)
  private String lastModifiedBy;

  @JsonView(Views.Extended.class)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date lastModifiedDate;

}
