package com.danit.models;

import com.danit.models.auditor.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "packages")
@NoArgsConstructor
@ToString(exclude = {"contracts"}, callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Data
public class Paket extends Auditable implements BaseEntity {

  @Id
  @SequenceGenerator(name = "paket_sequence", sequenceName = "paket_sequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paket_sequence")
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
  private Boolean active;

  @Column(name = "purchasable")
  private Boolean purchasable;

  @OneToMany(mappedBy = "paket", fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
  private List<Contract> contracts;

}
