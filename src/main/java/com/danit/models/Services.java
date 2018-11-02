package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "services")
public class Services {
  @Id
  @Column(name = "id")
  private UUID serviceId;

  @Column(name = "title")
  private String title;

  @Column(name = "price")
  private Float price;

  @Column(name = "cost")
  private Float cost;

  @Column(name = "unit")
  private String unit;

  @Column(name = "units_number")
  private int unitsNumber;

  @Column(name = "service_cathegory_id")
  private Long serviceCathegoryId;

  @Column(name = "active")
  private boolean isActive;
}
