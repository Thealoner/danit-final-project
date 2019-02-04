package com.danit.dto;

import com.danit.annotations.TargetClass;
import com.danit.utils.deserializers.CustomBaseEntityListDeserializer;
import com.danit.utils.serializers.CustomBaseEntityListSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "contracts")
@Data
public class ClientDto extends BaseDto {

  @JsonView(Views.Short.class)
  private String firstName;

  @JsonView(Views.Short.class)
  private String lastName;

  @JsonView(Views.Short.class)
  private String gender;

  @JsonView(Views.Short.class)
  private Date birthDate;

  @JsonView(Views.Short.class)
  private String phoneNumber;

  @JsonView(Views.Short.class)
  private String email;

  @JsonView(Views.Short.class)
  private Boolean active;

  @JsonDeserialize(using = CustomBaseEntityListDeserializer.class)
  @JsonSerialize(using = CustomBaseEntityListSerializer.class)
  @TargetClass(value = ContractDto.class, name = "contracts")
  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<ContractDto> contracts;

}
