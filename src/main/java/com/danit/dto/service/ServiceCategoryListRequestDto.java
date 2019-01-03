package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ServiceCategoryListRequestDto extends BaseListRequestDto {

  public String title;

  public String active;

}
