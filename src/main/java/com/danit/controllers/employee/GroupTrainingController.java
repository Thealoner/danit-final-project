package com.danit.controllers.employee;

import com.danit.models.employee.GroupTraining;
import com.danit.services.employee.GroupTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupTrainingController {

  private GroupTrainingService groupTrainingService;

  @Autowired
  public GroupTrainingController(GroupTrainingService groupTrainingService) {
    this.groupTrainingService = groupTrainingService;
  }

  @GetMapping("/group_training")
  public List<GroupTraining> getAllGroupTrainings() {
    return groupTrainingService.getAllGroupTrainings();
  }

  @GetMapping("/group_training/{id}")
  public GroupTraining getAllGroupTrainingById(@PathVariable long id) {
    return groupTrainingService.getGroupTrainingById(id);

  }

  @DeleteMapping("/group_training/{id}")
  public void deleteStudent(@PathVariable long id) {
    groupTrainingService.deleteGroupTraining(id);
  }

  @PostMapping("/group_training")
  public GroupTraining createGroupTraining(@RequestBody GroupTraining groupTraining) {
    return groupTrainingService.createGroupTraining(groupTraining);

  }

  @PutMapping("/group_training/{id}")
  public GroupTraining updateGroupTraining(@RequestBody GroupTraining groupTraining, @PathVariable(name = "id") long id) {
    return groupTrainingService.updateGroupTraining(groupTraining);
  }
}
