package com.danit.models;


import com.danit.models.auditor.Auditable;
import lombok.Data;
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


@NoArgsConstructor
@ToString(exclude = {"services"}, callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Table(name = "service_categories")
public class ServiceCategory extends Auditable implements BaseEntity {
  @Id
  @Column(name = "id")
  @SequenceGenerator(name = "service_category_sequence", sequenceName = "service_category_sequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_category_sequence")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "active")
  private Boolean active;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "serviceCategories")
  private List<Service> services;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ServiceCategory serviceCategory = (ServiceCategory) obj;
    return Objects.equals(id, serviceCategory.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
