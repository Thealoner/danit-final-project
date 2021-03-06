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
import java.util.Set;

@Entity
@Table(name = "employee")
@Data
public class Employee {
  @Id //NOSONAR
  @SequenceGenerator(name = "employeeSequence", sequenceName = "employeeSequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeSequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name")//NOSONAR
  private String firstName;

  @Column(name = "last_name")//NOSONAR
  private String lastName;

  @Column(name = "family_name")//NOSONAR
  private String familyName;

  @Column(name = "birth_date")//NOSONAR
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "departmentid")
  private Department department;

  @Column(name = "job_begin_date")
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.DATE)
  private Date jobBeginDate;

  @Column(name = "dismiss_date")
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.DATE)
  private Date dismissDate;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "positionid")
  private Position position;

  @Column(name = "email")
  private String email;

  @Column(name = "internal_number")
  private String internalNumber;

  @Column(name = "phone1")
  private String phone1;

  @Column(name = "phone2")
  private String phone2;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "employee_categoryid")
  private EmployeeCategory employeeCategory;

  @Column(name = "photo")
  private String photo;

  @Column(name = "cardid")
  private String cardId;

  @Column(name = "dbuser")
  private String dbUser;

  @Column(name = "discountid")
  private Long discountId;

  @Column(name = "gender")
  private int gender;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "employeeid", referencedColumnName = "id")
  private Set<EmployeeDiscount> employeeDiscountList;

}
