package ua.com.danit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class CardColorDto {

  @JsonView({Views.Extended.class})
  private Long id;

  @JsonView({Views.Extended.class})
  private String code;

  @JsonView({Views.Extended.class})
  private boolean active;

  @JsonView({Views.Extended.class})
  private Long contractId;

}
