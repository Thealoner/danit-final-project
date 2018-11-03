package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "packages")
public class Packet {

  @Id
  @Column(name = "id")
  private UUID packageId;
  @Column(name = "title")
  private String title;
  @Column(name = "term")
  private int term;
  @Column(name = "price")
  private Float price;
  @Column(name = "freeze_times")
  private int freezeTimes;
  @Column(name = "freeze_days")
  private int freezeDays;
  @Column(name = "freeze_min_term")
  private int freezeMinTerm;
  @Column(name = "access_without_card_times_limit")
  private int accessWithoutCardTimesLimit;
  @Column(name = "auto_activate_after_days")
  private int autoActivateAfterDays;
  @Column(name = "guest_visits")
  private int guestVisits;
  @Column(name = "open_date_allowed")
  private boolean openDateAllowed;
  @Column(name = "limit_additional_services")
  private boolean limitAdditionalServices;
  @Column(name = "limit_usage_by_payment_percentage")
  private boolean limitUsageByPaymentPercentage;
  //  @Column(name = "visit_time")
  //  private int visitTime;
  //  @Column(name = "visit_time_limited")
  //  private boolean visitTimeLimited;
  //  @Column(name = "card_color_id")
  //  private Long cardColorId;
  @Column(name = "active")
  private boolean isActive;
  @Column(name = "purchasable")
  private boolean isPurchasable;
  //  @Column(name = "parent_package_id")
  //  private Long parentPackageId;


  public Packet(UUID packageId, String title, int term, Float price, int freezeTimes, int freezeDays, int freezeMinTerm, int accessWithoutCardTimesLimit, int autoActivateAfterDays, int guestVisits, boolean openDateAllowed, boolean limitAdditionalServices, boolean limitUsageByPaymentPercentage, boolean isActive, boolean isPurchasable) {
    this.packageId = packageId;
    this.title = title;
    this.term = term;
    this.price = price;
    this.freezeTimes = freezeTimes;
    this.freezeDays = freezeDays;
    this.freezeMinTerm = freezeMinTerm;
    this.accessWithoutCardTimesLimit = accessWithoutCardTimesLimit;
    this.autoActivateAfterDays = autoActivateAfterDays;
    this.guestVisits = guestVisits;
    this.openDateAllowed = openDateAllowed;
    this.limitAdditionalServices = limitAdditionalServices;
    this.limitUsageByPaymentPercentage = limitUsageByPaymentPercentage;
    this.isActive = isActive;
    this.isPurchasable = isPurchasable;
  }

  public UUID getPackageId() {
    return packageId;
  }

  public void setPackageId(UUID packageId) {
    this.packageId = packageId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getTerm() {
    return term;
  }

  public void setTerm(int term) {
    this.term = term;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public int getFreezeTimes() {
    return freezeTimes;
  }

  public void setFreezeTimes(int freezeTimes) {
    this.freezeTimes = freezeTimes;
  }

  public int getFreezeDays() {
    return freezeDays;
  }

  public void setFreezeDays(int freezeDays) {
    this.freezeDays = freezeDays;
  }

  public int getFreezeMinTerm() {
    return freezeMinTerm;
  }

  public void setFreezeMinTerm(int freezeMinTerm) {
    this.freezeMinTerm = freezeMinTerm;
  }

  public int getAccessWithoutCardTimesLimit() {
    return accessWithoutCardTimesLimit;
  }

  public void setAccessWithoutCardTimesLimit(int accessWithoutCardTimesLimit) {
    this.accessWithoutCardTimesLimit = accessWithoutCardTimesLimit;
  }

  public int getAutoActivateAfterDays() {
    return autoActivateAfterDays;
  }

  public void setAutoActivateAfterDays(int autoActivateAfterDays) {
    this.autoActivateAfterDays = autoActivateAfterDays;
  }

  public int getGuestVisits() {
    return guestVisits;
  }

  public void setGuestVisits(int guestVisits) {
    this.guestVisits = guestVisits;
  }

  public boolean isOpenDateAllowed() {
    return openDateAllowed;
  }

  public void setOpenDateAllowed(boolean openDateAllowed) {
    this.openDateAllowed = openDateAllowed;
  }

  public boolean isLimitAdditionalServices() {
    return limitAdditionalServices;
  }

  public void setLimitAdditionalServices(boolean limitAdditionalServices) {
    this.limitAdditionalServices = limitAdditionalServices;
  }

  public boolean isLimitUsageByPaymentPercentage() {
    return limitUsageByPaymentPercentage;
  }

  public void setLimitUsageByPaymentPercentage(boolean limitUsageByPaymentPercentage) {
    this.limitUsageByPaymentPercentage = limitUsageByPaymentPercentage;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public boolean isPurchasable() {
    return isPurchasable;
  }

  public void setPurchasable(boolean purchasable) {
    isPurchasable = purchasable;
  }

  @Override
  public String toString() {
    return "Packet{" +
        "packageId=" + packageId +
        ", title='" + title + '\'' +
        ", term=" + term +
        ", price=" + price +
        ", freezeTimes=" + freezeTimes +
        ", freezeDays=" + freezeDays +
        ", freezeMinTerm=" + freezeMinTerm +
        ", accessWithoutCardTimesLimit=" + accessWithoutCardTimesLimit +
        ", autoActivateAfterDays=" + autoActivateAfterDays +
        ", guestVisits=" + guestVisits +
        ", openDateAllowed=" + openDateAllowed +
        ", limitAdditionalServices=" + limitAdditionalServices +
        ", limitUsageByPaymentPercentage=" + limitUsageByPaymentPercentage +
        ", isActive=" + isActive +
        ", isPurchasable=" + isPurchasable +
        '}';
  }
}
