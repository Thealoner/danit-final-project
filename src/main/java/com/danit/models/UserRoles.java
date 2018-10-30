package com.danit.models;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private UserRolesEnum role;

  public UserRoles() {
  }

  public UserRoles(UserRolesEnum role) {
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserRolesEnum getRole() {
    return role;
  }

  public void setRole(UserRolesEnum role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "UserRoles{" +
        "id=" + id +
        ", role=" + role +
        '}';
  }
}