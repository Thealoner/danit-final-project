package com.danit.services.employee;

import com.danit.models.employee.GroupTraining;

import java.util.List;

public interface GroupTrainingService {

  List<GroupTraining> getAllGroupTrainings();

  GroupTraining getGroupTrainingById(long id);

  GroupTraining createGroupTraining(GroupTraining groupTraining);

  GroupTraining updateGroupTraining(GroupTraining groupTraining);

  void deleteGroupTraining(long id);


}
