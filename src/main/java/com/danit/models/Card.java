package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cards")
public class Card implements Serializable {
  @Id
  @Column(name = "UUID")
  private UUID cardId;

  @Column(name = "expiration_date")
  private Date expirationDate;

  @Column(name = "gender")
  private String gender;

  @Column(name = "package_id")
  private UUID packageId;

  @Column(name = "credit")
  private Float credit;

  @Column(name = "client_id")
  private UUID clientId;

  public Card() {
  }

  public Card(UUID cardId, Date expirationDate, String gender, UUID packageId, Float credit, UUID clientId) {
    this.cardId = cardId;
    this.expirationDate = expirationDate;
    this.gender = gender;
    this.packageId = packageId;
    this.credit = credit;
    this.clientId = clientId;
  }

  public UUID getCardId() {
    return cardId;
  }

  public void setCardId(UUID cardId) {
    this.cardId = cardId;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public UUID getPackageId() {
    return packageId;
  }

  public void setPackageId(UUID packageId) {
    this.packageId = packageId;
  }

  public Float getCredit() {
    return credit;
  }

  public void setCredit(Float credit) {
    this.credit = credit;
  }

  public UUID getClientId() {
    return clientId;
  }

  public void setClientId(UUID clientId) {
    this.clientId = clientId;
  }
}
