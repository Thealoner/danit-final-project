package com.danit.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "service_categories")
public class ServiceCategory {
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "active")
  private Boolean isActive;


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "services_service_categories",
      joinColumns = @JoinColumn(name = "service_categories_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "services_id", referencedColumnName = "id")
      )
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
