package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
public class PageDataDto {

  @JsonView({Views.Extended.class, Views.Short.class})
  long totalElements;

  @JsonView({Views.Extended.class, Views.Short.class})
  int currentPage;

  @JsonView({Views.Extended.class, Views.Short.class})
  int pagesTotal;

  @JsonView({Views.Extended.class, Views.Short.class})
  int elementsPerPage;

  @JsonView({Views.Extended.class, Views.Short.class})
  int currentElements;

}
