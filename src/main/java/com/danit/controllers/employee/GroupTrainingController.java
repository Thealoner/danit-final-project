package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.employee.GroupTraining;
import com.danit.repositories.employee.GroupTrainingRepository;
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
public class GroupTrainingController {
  @Autowired
  private GroupTrainingRepository groupTrainingRepository;

  @GetMapping("/group_training")
  public List<GroupTraining> retrieveAllGroupTraining() {
    return groupTrainingRepository.findAll();
  }

  @GetMapping("/group_training/{id}")
  public GroupTraining retrieveStudent(@PathVariable long id) {
    Optional<GroupTraining> groupTraining = groupTrainingRepository.findById(id);

    if (!groupTraining.isPresent()) {
      throw new EntityNotFoundException("id-" + id);
    }
    return groupTraining.get();
  }

  @DeleteMapping("/group_training/{id}")
  public void deleteStudent(@PathVariable long id) {
    groupTrainingRepository.deleteById(id);
  }

  @PostMapping("/group_training")
  public ResponseEntity<Object> createGroupTraining(@RequestBody GroupTraining groupTraining) {
    GroupTraining savedGroupTraining = groupTrainingRepository.save(groupTraining);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedGroupTraining.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/group_training/{id}")
  public ResponseEntity<Object> updateStudent(@RequestBody GroupTraining groupTraining, @PathVariable Long id) {

    Optional<GroupTraining> studentOptional = groupTrainingRepository.findById(id);

    if (!studentOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    groupTraining.setId(id);

    groupTrainingRepository.save(groupTraining);

    return ResponseEntity.noContent().build();
  }
}
