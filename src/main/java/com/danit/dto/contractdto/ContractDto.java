package com.danit.dto.contractdto;

import com.danit.dto.clientdto.Views;
import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Date;

@Data
public class ContractDto {

  @JsonView(Views.Extended.class)
  private Long id;

  @JsonView(Views.Extended.class)
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private Date startDate;

  @JsonView(Views.Extended.class)
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private Date endDate;

  @JsonView(Views.Extended.class)
  private Float credit;

  @JsonView(Views.Extended.class)
  private boolean isActive;

}
