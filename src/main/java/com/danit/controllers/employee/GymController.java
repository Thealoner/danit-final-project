package com.danit.controllers.employee;

import com.danit.models.employee.Gym;
import com.danit.services.employee.GymService;
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
public class GymController {

  private GymService gymService;

  @Autowired
  public GymController(GymService gymService) {
    this.gymService = gymService;
  }

  @GetMapping("/gym")
  public List<Gym> getAllGyms(Pageable pageable, Principal principal) {
    return gymService.getAllGyms();
  }

  @GetMapping("/gym/{id}")
  public Gym getGymById(@PathVariable long id, Principal principal) {
    return gymService.getGymById(id);

  }

  @DeleteMapping("/gym/{id}")
  public void deleteGym(@PathVariable long id, Principal principal) {
    gymService.deleteGym(id);
  }

  @PostMapping("/gym")
  public Gym createGym(@RequestBody Gym gym, Principal principal) {
    return gymService.createGym(gym);

  }

  @PutMapping("/gym/{id}")
  public Gym updateGym(@RequestBody Gym gym, Principal principal, @PathVariable long id) {
    return gymService.updateGym(gym);

  }
}
