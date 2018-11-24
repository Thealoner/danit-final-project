package com.danit.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

@Entity
@Table(name = "card_colors")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
@NoArgsConstructor
@ToString(exclude = {"contract"})
@Data
public class Card {

  @Id
  @SequenceGenerator(name = "cardColorSequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cardColorSequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "card_code", unique = true)
  private String code;

  @Column(name = "card_active")
  private boolean active;

  @Column(name = "contract_id")
  private Long contractId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "contract_id", updatable = false, insertable = false)
  @JsonIgnore
  private Contract contract;

}

