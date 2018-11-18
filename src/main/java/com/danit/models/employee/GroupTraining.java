package com.danit.models.employee;

import com.danit.models.Services;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "group_training")
public class GroupTraining {
  @Id
  @SequenceGenerator(name = "groupTrainingSequence", sequenceName = "groupTrainingSequence",
      allocationSize = 1, initialValue = 1001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupTrainingSequence")
  @Column(name = "id")
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "employeeId")
  private Employee employee;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "gymId")
  private Gym gym;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "serviceId")
  private Services service;

  @Column(name = "amount")
  private int amount;

  @Column(name = "state")
  private int state;

  @Column(name = "description")
  private String description;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "training_typeId")
  private TrainingType trainingType;

}
