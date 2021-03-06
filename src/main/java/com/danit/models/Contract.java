package com.danit.models;


import com.danit.annotations.TargetClass;
import com.danit.models.auditor.Auditable;
import com.danit.utils.deserializers.CustomBaseEntityListDeserializer;
import com.danit.utils.serializers.CustomBaseEntityListSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@NoArgsConstructor
@JsonIgnoreProperties(value = {"paket", "client"}, allowSetters = true, ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"client", "paket", "cards"}, callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
public class Contract extends Auditable implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "start_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date startDate;

  @Column(name = "end_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date endDate;

  @Column(name = "credit")
  private Float credit;

  @Column(name = "active")
  private Boolean active;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "package_id")
  private Paket paket;

  @JsonDeserialize(using = CustomBaseEntityListDeserializer.class)
  @JsonSerialize(using = CustomBaseEntityListSerializer.class)
  @TargetClass(value = Card.class, name = "cards")
  @OneToMany(mappedBy = "contract", fetch = FetchType.EAGER)
  private List<Card> cards;

}
