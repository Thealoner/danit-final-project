package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CardDto extends BaseDto {

  @JsonView({Views.Ids.class, Views.Short.class})
  private Long id;

  @JsonView(Views.Short.class)
  private String code;

  @JsonView(Views.Short.class)
  private boolean active;

  @JsonView(Views.Short.class)
  private Long contractId;

}
