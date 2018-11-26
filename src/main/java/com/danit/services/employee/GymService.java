package com.danit.services.employee;

import com.danit.models.employee.Gym;

import java.util.List;

public interface GymService {

  List<Gym> getAllGyms();

  Gym getGymById(long id);

  Gym createGym(Gym gym);

  Gym updateGym(Gym gym);

  void deleteGym(long id);

  int getGymQty();

}
