package com.danit.models;


import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "contracts")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class Contract {
  @Id
  @SequenceGenerator(name = "contractSequence", sequenceName = "contractSequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractSequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "start_date")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Column(name = "end_date")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date endDate;

  @Column(name = "credit")
  private Float credit;

  @Column(name = "active")
  private boolean isActive;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id", updatable = false, insertable = false)
  @JsonIgnore
  private Client client;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "package_id", updatable = false, insertable = false)
  @JsonIgnore
  private Paket paket;

  @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  private List<CardColor> cards;

  @Column(name = "package_id")
  private Long packageId;

  @Column(name = "client_id")
  private Long clientId;

}
