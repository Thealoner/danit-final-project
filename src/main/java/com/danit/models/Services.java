package com.danit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "services")
public class Services {
  @Id
  @Column(name = "id")
  @SequenceGenerator(name = "servicesSequence", sequenceName = "servicesSequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicesSequence")
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

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "services")
  @JsonIgnore
  private List<ServiceCategory> serviceCategories;

  @Column(name = "active")
  private Boolean isActive;

  public Services() {
  }

  public Services(String title,
                  Float price, Float cost,
                  String unit, int unitsNumber,
                  List<ServiceCategory> serviceCategories,
                  Boolean isActive) {
    this.title = title;
    this.price = price;
    this.cost = cost;
    this.unit = unit;
    this.unitsNumber = unitsNumber;
    this.serviceCategories = serviceCategories;
    this.isActive = isActive;
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

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public Float getCost() {
    return cost;
  }

  public void setCost(Float cost) {
    this.cost = cost;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public int getUnitsNumber() {
    return unitsNumber;
  }

  public void setUnitsNumber(int unitsNumber) {
    this.unitsNumber = unitsNumber;
  }

  public List<ServiceCategory> getServiceCategories() {
    return serviceCategories;
  }

  public void setServiceCategories(List<ServiceCategory> serviceCategories) {
    this.serviceCategories = serviceCategories;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  @Override
  public String toString() {
    return "Services{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", price=" + price +
        ", cost=" + cost +
        ", unit='" + unit + '\'' +
        ", unitsNumber=" + unitsNumber +
        ", serviceCategories=" + serviceCategories +
        ", isActive=" + isActive +
        '}';
  }
}
