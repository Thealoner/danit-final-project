package com.danit.models.employee;

import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "discount")
public class Discount {
  @Id
  @SequenceGenerator(name = "discountSequence", sequenceName = "discountSequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discountSequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "percent")
  private float percent;

  @Column(name = "date_from")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private String dateFrom;

  @Column(name = "date_to")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private String dateTo;
}
