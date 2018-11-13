package com.danit.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class CardColor {

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  public Long getContractId() {
    return contractId;
  }

  public void setContractId(Long contractId) {
    this.contractId = contractId;
  }

  @Override
  public String toString() {
    return "CardColor{" +
        "id=" + id +
        ", code='" + code + '\'' +
        ", active=" + active +
        ", contractId=" + contractId +
        '}';
  }
}

