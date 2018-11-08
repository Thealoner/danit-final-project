package com.danit.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "card_colors")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class CardColor {

  @Id
  @Column(name = "card_id")
  private Long id;

  @Column(name = "card_code", unique = true)
  private String code;

  @Column(name = "card_active")
  private boolean active;

  @Column(name = "packet_id")
  private Long packetId;

  @Column(name = "contract_id")
  private Long contractId;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "packet_id", updatable = false, insertable = false)
  @JsonIgnore
  private Paket paket;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
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

  public Long getPacketId() {
    return packetId;
  }

  public void setPacketId(Long packetId) {
    this.packetId = packetId;
  }

  public Paket getPaket() {
    return paket;
  }

  public void setPaket(Paket paket) {
    this.paket = paket;
  }

  public Long getContractId() {
    return contractId;
  }

  public void setContractId(Long contractId) {
    this.contractId = contractId;
  }

  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  @Override
  public String toString() {
    return "CardColor{" +
        "id=" + id +
        ", code='" + code + '\'' +
        ", active=" + active +
        ", packetId=" + packetId +
        ", contractId=" + contractId +
        '}';
  }
}

