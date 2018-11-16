package com.danit.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@NoArgsConstructor
@ToString(exclude = {"contracts", "cards"})
@Data
public class Paket {

  @Id
  @SequenceGenerator(name = "paketSequence", sequenceName = "paketSequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paketSequence")
  @Column(name = "id")
  private Long id;

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
  private Boolean openDateAllowed;

  @Column(name = "users_min")
  private int usersMin;

  @Column(name = "limit_visit_time")
  private Boolean limitVisitTime;

  @Column(name = "visit_time")
  private int visitTime;

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

  @OneToMany(mappedBy = "paket", fetch = FetchType.EAGER)
  private List<CardColor> cards;

}
