package com.danit.models.employee;


import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "employee_discount")
class EmployeeDiscount {
  @Id
  @SequenceGenerator(name = "empDiscountSequence", sequenceName = "empDiscountSequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empDiscountSequence")
  @Column(name = "id")
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "discountId")
  private Discount discount;

  @Column(name = "valid_from")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private Date validFrom;

  @Column(name = "employeeId")
  private Long employeeId;

}
