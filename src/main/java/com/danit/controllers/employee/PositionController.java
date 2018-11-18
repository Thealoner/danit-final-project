package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.employee.Position;
import com.danit.repositories.employee.PositionRepository;
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
public class PositionController {

  @Autowired
  private PositionRepository positionRepository;

  @GetMapping("/position")
  public List<Position> retrieveAllPost() {
    return positionRepository.findAll();
  }

  @GetMapping("/position/{id}")
  public Position retrievePost(@PathVariable long id) {
    Optional<Position> post = positionRepository.findById(id);

    if (!post.isPresent()) {
      throw new EntityNotFoundException("id-" + id);
    }
    return post.get();
  }

  @DeleteMapping("/position/{id}")
  public void deletePost(@PathVariable long id) {
    positionRepository.deleteById(id);
  }

  @PostMapping("/position")
  public ResponseEntity<Object> createPost(@RequestBody Position position) {
    Position savedPosition = positionRepository.save(position);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedPosition.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/position/{id}")
  public ResponseEntity<Object> updatepost(@RequestBody Position position, @PathVariable long id) {

    Optional<Position> postOptional = positionRepository.findById(id);

    if (!postOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    position.setId(id);

    positionRepository.save(position);

    return ResponseEntity.noContent().build();
  }
}
