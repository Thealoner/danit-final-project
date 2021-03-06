package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ServiceDto extends BaseDto {

  @JsonView(Views.Short.class)
  private String title;

  @JsonView(Views.Short.class)
  private Float price;

  @JsonView(Views.Extended.class)
  private Float cost;

  @JsonView(Views.Extended.class)
  private String unit;

  @JsonView(Views.Extended.class)
  private Integer unitsNumber;

  @JsonView(Views.Short.class)
  private Boolean active;

}
