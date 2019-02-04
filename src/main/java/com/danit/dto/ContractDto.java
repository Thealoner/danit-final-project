package com.danit.dto;

import com.danit.annotations.TargetClass;
import com.danit.utils.deserializers.CustomBaseEntityListDeserializer;
import com.danit.utils.serializers.CustomBaseEntityListSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "cards")
@Data
public class ContractDto extends BaseDto {

  @JsonView(Views.Short.class)
  private Date startDate;

  @JsonView(Views.Short.class)
  private Date endDate;

  @JsonView(Views.Short.class)
  private Float credit;

  @JsonView(Views.Short.class)
  private Long packageId;

  @JsonView(Views.Short.class)
  private Long clientId;

  @JsonView(Views.Short.class)
  private Boolean active;

  @JsonDeserialize(using = CustomBaseEntityListDeserializer.class)
  @JsonSerialize(using = CustomBaseEntityListSerializer.class)
  @TargetClass(value = CardDto.class, name = "cards")
  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<CardDto> cards;

}
