package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_cathegories")
public class ServiceCathegory {
  @Id
  @Column(name = "id")
  private Long serviceCathegoryId;

  @Column(name = "title")
  private String serviceCathegoryTitle;

  @Column(name = "active")
  private boolean isActive;
}
