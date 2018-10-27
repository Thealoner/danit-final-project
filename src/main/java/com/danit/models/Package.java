package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "packages")
public class Package {

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
  private int openDateAllowed;
  @Column(name = "users_min")
  private int usersMin;
  //  private usersFamilyRelashionship;
//  private limitAdditionalServices;
//  private limitUsageByPaymentPercentage;
  @Column(name = "limit_visit_time")
  private int limitVisitTime;
//  private sessionLimit;
//  private Long cardColorID;
//  private Long packageTypeID;
//  private Long scheduleID;
}
