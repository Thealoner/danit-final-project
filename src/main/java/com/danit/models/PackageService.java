package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "packages_services")
public class PackageService {

  @Column(name = "package_id")
  private UUID packageId;

  @Column(name = "service_id")
  private UUID serviceId;

  @Column(name = "service_rule_id")
  private Long serviceRuleId;

  @Column(name = "organization_id")
  private Long organizationId;

  @Column(name = "service_qty")
  private int serviceQuantity;
}
