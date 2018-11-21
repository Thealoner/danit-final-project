package com.danit.controllers.employee;

import com.danit.models.employee.Gym;
import com.danit.services.employee.GymService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GymController {

  private GymService gymService;

  @GetMapping("/gym")
  public List<Gym> getAllGyms() {
    return gymService.getAllGyms();
  }

  @GetMapping("/gym/{id}")
  public Gym getGymById(@PathVariable long id) {
    return gymService.getGymById(id);

  }

  @DeleteMapping("/gym/{id}")
  public void deleteGym(@PathVariable long id) {
    gymService.deleteGym(id);
  }

  @PostMapping("/gym")
  public Gym createGym(@RequestBody Gym gym) {
    return gymService.createGym(gym);

  }

  @PutMapping("/gym/{id}")
  public Gym updateGym(@RequestBody Gym gym, @PathVariable long id) {
    return gymService.updateGym(gym);

  }
}
