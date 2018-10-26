package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "organizations")
public class Organization {
  @Id
  @Column(name = "id")
  private Long organizationID;

  @Column(name = "title")
  private String organizationTitle;


}
