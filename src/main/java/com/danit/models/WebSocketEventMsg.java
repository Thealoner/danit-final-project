package com.danit.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebSocketEventMsg {

  private Long id;

  private String entityName;

}
