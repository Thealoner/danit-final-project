package com.danit.models.eployee;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "company")
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "sname")
  private String sname;

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
