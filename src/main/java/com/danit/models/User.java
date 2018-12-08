package com.danit.models;

import com.danit.models.auditor.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
@ToString(exclude = {"roles"}, callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@NoArgsConstructor
@Data
public class User extends Auditable implements BaseEntity {
  @Id
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  @Column(name = "id")
  private Long id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<UserRoles> roles;

  public User(String username, String password, Collection<UserRoles> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

}
