package com.danit.models;


import com.danit.utils.CustomDateDeserializer;
import com.danit.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
@NoArgsConstructor
@ToString(exclude = {"client", "paket", "cards"})
@Data
public class Contract {
  @Id
  @SequenceGenerator(name = "contractSequence", sequenceName = "contractSequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contractSequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "start_date")
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.TIMESTAMP)
  private Date startDate;

  @Column(name = "end_date")
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  @Temporal(TemporalType.TIMESTAMP)
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
  private List<Card> cards;

  @Column(name = "package_id")
  private Long packageId;

  @Column(name = "client_id")
  private Long clientId;

}
