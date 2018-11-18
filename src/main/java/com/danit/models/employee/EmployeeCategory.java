package com.danit.models.employee;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "employee_category")
public class EmployeeCategory {
  @Id
  @SequenceGenerator(name = "empCategorySequence", sequenceName = "empCategorySequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empCategorySequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

}
