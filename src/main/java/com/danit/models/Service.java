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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(exclude = {"serviceCategories"}, callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Table(name = "services")
public class Service extends Auditable implements BaseEntity {
  @Id
  @Column(name = "id")
  @SequenceGenerator(name = "services_sequence", sequenceName = "services_sequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "services_sequence")
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Service service = (Service) obj;
    return Objects.equals(id, service.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
