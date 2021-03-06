package com.danit.models;

import com.danit.annotations.TargetClass;
import com.danit.models.auditor.Auditable;
import com.danit.utils.deserializers.CustomBaseEntityListDeserializer;
import com.danit.utils.serializers.CustomBaseEntityListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "packages")
@NoArgsConstructor
@ToString(exclude = {"contracts"}, callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
public class Paket extends Auditable implements BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @EqualsAndHashCode.Include
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

  @JsonDeserialize(using = CustomBaseEntityListDeserializer.class)
  @JsonSerialize(using = CustomBaseEntityListSerializer.class)
  @TargetClass(value = Contract.class, name = "contracts")
  @OneToMany(mappedBy = "paket", fetch = FetchType.EAGER)
  private List<Contract> contracts;

}
