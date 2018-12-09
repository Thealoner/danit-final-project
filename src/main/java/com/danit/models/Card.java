package com.danit.models;


import com.danit.models.auditor.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cards")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
@NoArgsConstructor
@ToString(exclude = {"contract"})
@Data
public class Card extends Auditable implements BaseEntity {

  @Id
  @SequenceGenerator(name = "card_sequence", sequenceName = "card_sequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "card_code", unique = true)
  private String code;

  @Column(name = "card_active")
  private Boolean active;

  @Column(name = "contract_id")
  private Long contractId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "contract_id", updatable = false, insertable = false)
  @JsonIgnore
  private Contract contract;

}

