package com.danit.models.employee;

import com.danit.utils.CustomDateDeserializer;
import com.danit.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

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
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.DATE)
  private Date dateFrom;

  @Column(name = "date_to")
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.DATE)
  private Date dateTo;
}
