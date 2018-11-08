package com.danit.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "service_categories")
public class ServiceCategory {
  @Id
  @Column(name = "id")
  private Long serviceId;

  @Column(name = "title")
  private String title;

  @Column(name = "active")
  private boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      },
      mappedBy = "serviceCategories")
  @JsonIdentityReference(alwaysAsId = true)
  private List<Services> services;
}
