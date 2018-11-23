package com.danit.controllers.employee;

import com.danit.models.employee.Position;
import com.danit.services.employee.PositionService;
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
public class PositionController {

  private PositionService positionService;

  @Autowired
  public PositionController(PositionService positionService) {
    this.positionService = positionService;
  }

  @GetMapping("/position")
  public List<Position> getAllPositions() {
    return positionService.getAllPositions();
  }

  @GetMapping("/position/{id}")
  public Position getPositionById(@PathVariable long id) {
    return positionService.getPositionById(id);

  }

  @DeleteMapping("/position/{id}")
  public void deletePosition(@PathVariable long id) {
    positionService.deletePosition(id);
  }

  @PostMapping("/position")
  public Position createPosition(@RequestBody Position position) {
    return positionService.createPosition(position);

  }

  @PutMapping("/position/{id}")
  public Position updatePosition(@RequestBody Position position, @PathVariable long id) {
    return positionService.updatePosition(position);
  }
}
