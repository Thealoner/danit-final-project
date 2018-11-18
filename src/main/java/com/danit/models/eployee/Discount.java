package com.danit.models.eployee;

import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "discount")
public class Discount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "percent")
  private String percent;

  @Column(name = "datefrom")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private String dateFrom;

  @Column(name = "dateto")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private String dateTo;
}
