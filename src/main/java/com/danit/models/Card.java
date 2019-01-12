package com.danit.models;


import com.danit.models.auditor.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Table(name = "cards")
@NoArgsConstructor
@ToString(exclude = {"contract"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
public class Card extends Auditable implements BaseEntity {

  @Id
  @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
  @Column(name = "id")
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "card_code", unique = true)
  private String code;

  @Column(name = "card_active")
  private Boolean active;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "contract_id")
  @JsonIgnore
  private Contract contract;

}

