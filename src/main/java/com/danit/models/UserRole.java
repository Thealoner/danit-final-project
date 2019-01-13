package com.danit.models;

import com.danit.models.auditor.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
public class UserRole extends Auditable implements BaseEntity {

  @Id
  @Column(name = "id")
  @SequenceGenerator(name = "user_role_sequence", sequenceName = "user_role_sequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_sequence")
  @EqualsAndHashCode.Include
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "role")
  private UserRolesEnum role;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
  @JsonIgnore
  private List<User> users;

}