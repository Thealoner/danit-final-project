package com.danit.models;


import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contracts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract {
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "start_date")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "dd-MM-yyyy")
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Column(name = "end_date")
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "dd-MM-yyyy")
  @Temporal(TemporalType.DATE)
  private Date endDate;

  @Column(name = "credit")
  private Float credit;

  @Column(name = "active")
  private boolean isActive;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "client_id", updatable = false, insertable = false)
  @JsonIgnore
  private Client client;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "package_id", updatable = false, insertable = false)
  @JsonIgnore
  private Paket paket;

  @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private List<CardColor> cards;

  @Column(name = "package_id")
  private Long packageId;

  @Column(name = "client_id")
  private Long clientId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Float getCredit() {
    return credit;
  }

  public void setCredit(Float credit) {
    this.credit = credit;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Paket getPaket() {
    return paket;
  }

  public void setPaket(Paket paket) {
    this.paket = paket;
  }

  public List<CardColor> getCards() {
    return cards;
  }

  public void setCards(List<CardColor> cards) {
    this.cards = cards;
  }

  public Long getPackageId() {
    return packageId;
  }

  public void setPackageId(Long packageId) {
    this.packageId = packageId;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  @Override
  public String toString() {
    return "Contract{" +
        "id=" + id +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", credit=" + credit +
        ", isActive=" + isActive +
        ", client=" + client +
        ", paket=" + paket +
        ", packageId=" + packageId +
        ", clientId=" + clientId +
        '}';
  }
}
