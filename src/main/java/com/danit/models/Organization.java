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
  @SequenceGenerator(name = "organizationSequence", sequenceName = "organizationSequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizationSequence")
  @Column(name = "id")
  private Long organizationId;

  @Column(name = "title")
  private String organizationTitle;


}
