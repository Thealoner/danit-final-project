package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "contracts")
public class Contract implements Serializable {
  @Id
  @Column(name = "id")
  private UUID contractId;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @Column(name = "gender")
  private String gender;

  @Column(name = "credit")
  private Float credit;

  @Column(name = "package_id")
  private UUID packageId;

  @Column(name = "client_id")
  private UUID clientId;


  @Column(name = "active")
  private boolean isActive;

  public Contract() {
  }
}
