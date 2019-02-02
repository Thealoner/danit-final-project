package com.danit.models;

import com.danit.models.auditor.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@ToString(exclude = {"serviceCategories"}, callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@Entity
@Table(name = "services")
public class Service extends Auditable implements BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
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

  @ManyToMany(fetch = FetchType.EAGER)
  @JsonIgnore
  private List<ServiceCategory> serviceCategories;

  @Column(name = "active")
  private Boolean active;

}
