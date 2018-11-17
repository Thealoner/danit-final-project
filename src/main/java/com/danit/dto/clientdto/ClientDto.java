package com.danit.dto.clientdto;

import com.danit.dto.contractdto.ContractDto;
import com.danit.dto.contractdto.ContractIdDto;
import com.danit.models.Contract;
import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Data
public class ClientDto {

  @JsonView(Views.Extended.class)
  private Long id;

  @JsonView(Views.Short.class)
  private String firstName;

  @JsonView(Views.Short.class)
  private String lastName;

  @JsonView(Views.Short.class)
  private String gender;

  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  @JsonView(Views.Extended.class)
  private Date birthDate;

  @JsonView(Views.Extended.class)
  private String phoneNumber;

  @JsonView(Views.Extended.class)
  private String email;

  @JsonView(Views.Extended.class)
  private Boolean active;

  @JsonView(Views.Extended.class)
  private List<ContractDto> contracts;
}
