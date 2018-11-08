package com.danit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "services")
public class Services {
  @Id
  @Column(name = "id")
  private Long id;

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

  @ManyToMany(cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(name = "service_category_services",
      joinColumns = @JoinColumn(name = "service_id"),
      inverseJoinColumns = @JoinColumn(name = "service_category_id")
  )
  @JsonIgnore
  private List<ServiceCategory> serviceCategories;

  @Column(name = "active")
  private boolean enabled;
}
