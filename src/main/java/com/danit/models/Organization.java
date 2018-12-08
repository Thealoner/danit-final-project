package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "organizations")
public class Organization {
  @Id
  @SequenceGenerator(name = "organization_sequence", sequenceName = "organization_sequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String organizationTitle;


}
