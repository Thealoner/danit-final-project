package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "services")
@Data
public class ServiceCategoryDto extends BaseDto {

  @JsonView({Views.Ids.class, Views.Short.class})
  private Long id;

  @JsonView(Views.Short.class)
  private String title;

  @JsonView(Views.Short.class)
  private Boolean isActive;

  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<ServiceDto> services;
}
