package com.danit.dto.service;

import com.danit.dto.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Data
public class PageDataDto {

  @JsonView({Views.Short.class, Views.Ids.class})
  long totalElements;

  @JsonView({Views.Short.class, Views.Ids.class})
  int currentPage;

  @JsonView({Views.Short.class, Views.Ids.class})
  int pagesTotal;

  @JsonView({Views.Short.class, Views.Ids.class})
  int elementsPerPage;

  @JsonView({Views.Short.class, Views.Ids.class})
  int currentElements;

}
