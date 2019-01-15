package com.danit.controllers.employee;

import com.danit.models.employee.TrainingType;
import com.danit.services.employee.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TrainingTypeController {

  private TrainingTypeService trainingTypeService;

  @Autowired
  public TrainingTypeController(TrainingTypeService trainingTypeService) {
    this.trainingTypeService = trainingTypeService;
  }

  @GetMapping("/training_type")
  public List<TrainingType> getAllTrainingTypes(Pageable pageable, Principal principal) {
    return trainingTypeService.getAllTrainingTypes();
  }

  @GetMapping("/training_type/{id}")
  public TrainingType getTrainingType(@PathVariable long id, Principal principal) {
    return trainingTypeService.getTrainingTypeById(id);

  }

  @DeleteMapping("/training_type/{id}")
  public void deleteStudent(@PathVariable long id, Principal principal) {
    trainingTypeService.deleteTrainingType(id);
  }

  @PostMapping("/training_type")
  public TrainingType createTrainingType(@RequestBody TrainingType trainingType, Principal principal) {
    return trainingTypeService.createTrainingType(trainingType);

  }

  @PutMapping("/training_type/{id}")
  public TrainingType updateTrainingType(@RequestBody TrainingType trainingType, Principal principal,
                                         @PathVariable long id) {
    return trainingTypeService.updateTrainingType(trainingType);
  }
}
