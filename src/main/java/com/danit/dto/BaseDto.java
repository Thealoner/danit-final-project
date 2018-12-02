package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {

  @JsonView(Views.Extended.class)
  private String createdBy;

  @JsonView(Views.Extended.class)
  private Date creationDate;

  @JsonView(Views.Extended.class)
  private String lastModifiedBy;

  @JsonView(Views.Extended.class)
  private Date lastModifiedDate;
}
