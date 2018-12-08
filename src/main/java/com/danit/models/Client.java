package com.danit.models;


import com.danit.models.auditor.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
@NoArgsConstructor
@ToString(exclude = {"contracts"}, callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Data
public class Client extends Auditable implements BaseEntity {

  @Id
  @SequenceGenerator(name = "clientSequence", sequenceName = "clientSequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientSequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "gender")
  private String gender;

  @Column(name = "birth_date")
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "active")
  private Boolean active;

  @OneToMany(mappedBy = "clientId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<Contract> contracts;

}
