package com.danit.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoles {
  @Id
  @Enumerated(value = EnumType.STRING)
  private UserRolesEnum role;

  public UserRoles() {
  }

  public UserRoles(UserRolesEnum role) {
    this.role = role;
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
        "role=" + role +
        '}';
  }
}