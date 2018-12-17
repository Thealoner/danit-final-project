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

@EqualsAndHashCode(callSuper = true)
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

}
