package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.employee.Gym;
import com.danit.repositories.employee.GymRepository;
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
public class GymController {
  @Autowired
  private GymRepository gymRepository;

  @GetMapping("/gym")
  public List<Gym> retrieveAllGym() {
    return gymRepository.findAll();
  }

  @GetMapping("/gym/{id}")
  public Gym retrieveStudent(@PathVariable long id) {
    Optional<Gym> gym = gymRepository.findById(id);

    if (!gym.isPresent()) {
      throw new EntityNotFoundException("id-" + id);
    }
    return gym.get();
  }

  @DeleteMapping("/gym/{id}")
  public void deleteStudent(@PathVariable long id) {
    gymRepository.deleteById(id);
  }

  @PostMapping("/gym")
  public ResponseEntity<Object> createGym(@RequestBody Gym gym) {
    Gym savedGym = gymRepository.save(gym);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedGym.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/gym/{id}")
  public ResponseEntity<Object> updateStudent(@RequestBody Gym gym, @PathVariable long id) {

    Optional<Gym> studentOptional = gymRepository.findById(id);

    if (!studentOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    gym.setId(id);

    gymRepository.save(gym);

    return ResponseEntity.noContent().build();
  }
}
