package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
public class PageDataDto {

  @JsonView({Views.Extended.class, Views.Short.class, Views.Ids.class})
  long totalElements;

  @JsonView({Views.Extended.class, Views.Short.class, Views.Ids.class})
  int currentPage;

  @JsonView({Views.Extended.class, Views.Short.class, Views.Ids.class})
  int pagesTotal;

  @JsonView({Views.Extended.class, Views.Short.class, Views.Ids.class})
  int elementsPerPage;

  @JsonView({Views.Extended.class, Views.Short.class, Views.Ids.class})
  int currentElements;

}
