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
  private UUID cardID;

  @Column(name = "expiration_date")
  private Date expirationDate;

  @Column(name = "gender")
  private String gender;

  @Column(name = "package_id")
  private UUID packageID;

  @Column(name = "credit")
  private Float credit;

  @Column(name = "client_id")
  private UUID clientID;

  public Card() {
  }

  public Card(UUID cardID, Date expirationDate, String gender, UUID packageID, Float credit, UUID clientID) {
    this.cardID = cardID;
    this.expirationDate = expirationDate;
    this.gender = gender;
    this.packageID = packageID;
    this.credit = credit;
    this.clientID = clientID;
  }

  public UUID getCardID() {
    return cardID;
  }

  public void setCardID(UUID cardID) {
    this.cardID = cardID;
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

  public UUID getPackageID() {
    return packageID;
  }

  public void setPackageID(UUID packageID) {
    this.packageID = packageID;
  }

  public Float getCredit() {
    return credit;
  }

  public void setCredit(Float credit) {
    this.credit = credit;
  }

  public UUID getClientID() {
    return clientID;
  }

  public void setClientID(UUID clientID) {
    this.clientID = clientID;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
