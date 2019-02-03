package com.danit.models.employee;

import com.danit.utils.deserializers.CustomDateDeserializer;
import com.danit.utils.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "department")
public class Department {

  @Id
  @SequenceGenerator(name = "departmentSequence", sequenceName = "departmentSequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentSequence")
  @Column(name = "id")
  private Long id;

  @OneToMany
  @JoinColumn(name = "pid")
  private List<Department> child;

  @Column(name = "short_name", nullable = false)
  private String shortName;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "date_from", nullable = false)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.DATE)
  private Date dateFrom;

  @Column(name = "date_to")
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.DATE)
  private Date dateTo;

  @Column(name = "hier_level", nullable = false)
  private int hierLevel;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "companyid")
  private Company company;

  @Column(name = "sort_position")
  private String sortPosition;

}

