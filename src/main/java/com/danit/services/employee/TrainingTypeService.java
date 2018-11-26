package com.danit.services.employee;

import com.danit.models.employee.TrainingType;

import java.util.List;

public interface TrainingTypeService {

  List<TrainingType> getAllTrainingTypes();

  TrainingType getTrainingTypeById(long id);

  TrainingType createTrainingType(TrainingType trainingType);

  TrainingType updateTrainingType(TrainingType trainingType);

  void deleteTrainingType(long id);

  boolean trainingTypeExists(long id);

  int getTrainingTypeQty();
}
