package com.danit.models;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Table(name = "contracts")
public class Contract implements Serializable {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID contractId;

  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @Column(name = "credit")
  private Float credit;

/*  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "package_id", referencedColumnName = "id")
  private Packet packet;*/

/*  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "client_id", referencedColumnName = "id")
  private Client client;*/


  @Column(name = "active")
  private boolean isActive;

  public Contract() {
  }

}
