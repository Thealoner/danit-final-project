package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CardDto extends BaseDto {

  @JsonView(Views.Short.class)
  private String code;

  @JsonView(Views.Short.class)
  private Boolean active;

  @JsonView(Views.Short.class)
  private Long contractId;

}
