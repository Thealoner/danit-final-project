package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.eployee.TrainingType;
import com.danit.repositories.employee.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TrainingTypeController {
  @Autowired
  private TrainingTypeRepository trainingTypeRepository;

  @GetMapping("/trainingtype")
  public List<TrainingType> retrieveAllTrainingType() {
    return trainingTypeRepository.findAll();
  }

  @GetMapping("/trainingType/{id}")
  public TrainingType retrieveStudent(@PathVariable long id) {
    Optional<TrainingType> trainingType = trainingTypeRepository.findById(id);

    if (!trainingType.isPresent()) {
      throw new EntityNotFoundException("id-" + id);
    }
    return trainingType.get();
  }

  @DeleteMapping("/trainingtype/{id}")
  public void deleteStudent(@PathVariable long id) {
    trainingTypeRepository.deleteById(id);
  }

  @PostMapping("/trainingtype")
  public ResponseEntity<Object> createTrainingType(@RequestBody TrainingType trainingType) {
    TrainingType savedTrainingType = trainingTypeRepository.save(trainingType);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedTrainingType.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/trainingtype/{id}")
  public ResponseEntity<Object> updateStudent(@RequestBody TrainingType trainingType, @PathVariable long id) {

    Optional<TrainingType> studentOptional = trainingTypeRepository.findById(id);

    if (!studentOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    trainingType.setId(id);

    trainingTypeRepository.save(trainingType);

    return ResponseEntity.noContent().build();
  }
}
