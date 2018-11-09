package com.danit.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceCategory {
  @Id
  @Column(name = "service_category_id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "active")
  private Boolean isActive;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "serviceCategories")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  private List<Services> services;

  public ServiceCategory() {
  }

  public ServiceCategory(Long id, String title, Boolean isActive, List<Services> services) {
    this.id = id;
    this.title = title;
    this.isActive = isActive;
    this.services = services;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public List<Services> getServices() {
    return services;
  }

  public void setServices(List<Services> services) {
    this.services = services;
  }

  @Override
  public String toString() {
    return "ServiceCategory{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", isActive=" + isActive +
        ", services=" + services +
        '}';
  }
}
