package com.danit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "packages")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paket {

  @Id
  @SequenceGenerator(name = "paketSequence", sequenceName = "paketSequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paketSequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "term")
  private Integer term;

  @Column(name = "price")
  private Float price;

  @Column(name = "freeze_times")
  private Integer freezeTimes;

  @Column(name = "freeze_days")
  private Integer freezeDays;

  @Column(name = "freeze_min_term")
  private Integer freezeMinTerm;

  @Column(name = "access_without_card_times_limit")
  private Integer accessWithoutCardTimesLimit;

  @Column(name = "auto_activate_after_days")
  private Integer autoActivateAfterDays;

  @Column(name = "guest_visits")
  private Integer guestVisits;

  @Column(name = "open_date_allowed")
  private Boolean openDateAllowed;

  @Column(name = "users_min")
  private Integer usersMin;

  @Column(name = "limit_visit_time")
  private Boolean limitVisitTime;

  @Column(name = "visit_time")
  private Integer visitTime;

  @Column(name = "limit_additional_services")
  private Boolean limitAdditionalServices;

  @Column(name = "limit_usage_by_payment_percentage")
  private Boolean limitUsageByPaymentPercentage;

  @Column(name = "active")
  private Boolean isActive;

  @Column(name = "purchasable")
  private Boolean purchasable;

  @OneToMany(mappedBy = "packageId", fetch = FetchType.EAGER)
  private List<Contract> contracts;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getTerm() {
    return term;
  }

  public void setTerm(Integer term) {
    this.term = term;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public Integer getFreezeTimes() {
    return freezeTimes;
  }

  public void setFreezeTimes(Integer freezeTimes) {
    this.freezeTimes = freezeTimes;
  }

  public Integer getFreezeDays() {
    return freezeDays;
  }

  public void setFreezeDays(Integer freezeDays) {
    this.freezeDays = freezeDays;
  }

  public Integer getFreezeMinTerm() {
    return freezeMinTerm;
  }

  public void setFreezeMinTerm(Integer freezeMinTerm) {
    this.freezeMinTerm = freezeMinTerm;
  }

  public Integer getAccessWithoutCardTimesLimit() {
    return accessWithoutCardTimesLimit;
  }

  public void setAccessWithoutCardTimesLimit(Integer accessWithoutCardTimesLimit) {
    this.accessWithoutCardTimesLimit = accessWithoutCardTimesLimit;
  }

  public Integer getAutoActivateAfterDays() {
    return autoActivateAfterDays;
  }

  public void setAutoActivateAfterDays(Integer autoActivateAfterDays) {
    this.autoActivateAfterDays = autoActivateAfterDays;
  }

  public Integer getGuestVisits() {
    return guestVisits;
  }

  public void setGuestVisits(Integer guestVisits) {
    this.guestVisits = guestVisits;
  }

  public Boolean getOpenDateAllowed() {
    return openDateAllowed;
  }

  public void setOpenDateAllowed(Boolean openDateAllowed) {
    this.openDateAllowed = openDateAllowed;
  }

  public Integer getUsersMin() {
    return usersMin;
  }

  public void setUsersMin(Integer usersMin) {
    this.usersMin = usersMin;
  }

  public Boolean getLimitVisitTime() {
    return limitVisitTime;
  }

  public void setLimitVisitTime(Boolean limitVisitTime) {
    this.limitVisitTime = limitVisitTime;
  }

  public Integer getVisitTime() {
    return visitTime;
  }

  public void setVisitTime(Integer visitTime) {
    this.visitTime = visitTime;
  }

  public Boolean getLimitAdditionalServices() {
    return limitAdditionalServices;
  }

  public void setLimitAdditionalServices(Boolean limitAdditionalServices) {
    this.limitAdditionalServices = limitAdditionalServices;
  }

  public Boolean getLimitUsageByPaymentPercentage() {
    return limitUsageByPaymentPercentage;
  }

  public void setLimitUsageByPaymentPercentage(Boolean limitUsageByPaymentPercentage) {
    this.limitUsageByPaymentPercentage = limitUsageByPaymentPercentage;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Boolean getPurchasable() {
    return purchasable;
  }

  public void setPurchasable(Boolean purchasable) {
    this.purchasable = purchasable;
  }

  public List<Contract> getContracts() {
    return contracts;
  }

  public void setContracts(List<Contract> contracts) {
    this.contracts = contracts;
  }

  @Override
  public String toString() {
    return "Paket{" +
        "id=" + id +
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
        ", usersMin=" + usersMin +
        ", limitVisitTime=" + limitVisitTime +
        ", visitTime=" + visitTime +
        ", limitAdditionalServices=" + limitAdditionalServices +
        ", limitUsageByPaymentPercentage=" + limitUsageByPaymentPercentage +
        ", isActive=" + isActive +
        ", purchasable=" + purchasable +
        '}';
  }
}
