package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "services")
@Data
public class ServiceCategoryDto extends BaseDto {

  @JsonView(Views.Short.class)
  private String title;

  @JsonView(Views.Short.class)
  private Boolean active;

  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<ServiceDto> services;
}
