package com.danit.models.employee;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "company")
public class Company {
  @Id
  @SequenceGenerator(name = "companySequence", sequenceName = "companySequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companySequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "short_name")
  private String shortName;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "phone2")
  private String phone2;

  @Column(name = "email")
  private String email;

  @Column(name = "address")
  private String address;

  @Column(name = "orgcode")
  private String orgcode;
}
