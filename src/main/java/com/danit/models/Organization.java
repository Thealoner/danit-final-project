package com.danit.models;

import com.danit.models.auditor.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "organizations")
@Data
public class Organization extends Auditable implements BaseEntity {
  @Id
  @SequenceGenerator(name = "organization_sequence", sequenceName = "organization_sequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String organizationTitle;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Organization organization = (Organization) obj;
    return Objects.equals(id, organization.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
