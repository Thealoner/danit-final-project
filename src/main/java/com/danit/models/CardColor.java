package com.danit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "card_colors")
public class CardColor {

  @Id
  @Column(name = "id")
  private Long cardColorId;
  @Column(name = "code")
  private String code;
  @Column(name = "active")
  private boolean isActive;
}
