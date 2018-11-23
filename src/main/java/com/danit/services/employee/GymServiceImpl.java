package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.Gym;
import com.danit.repositories.employee.GymRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.danit.utils.ServiceUtils.updateNonEqualFields;

@Service
public class GymServiceImpl implements GymService {

  private GymRepository gymRepository;

  public GymServiceImpl(GymRepository gymRepository) {
    this.gymRepository = gymRepository;
  }

  @Override
  public List<Gym> getAllGyms() {
    return gymRepository.findAll();
  }

  @Override
  public Gym getGymById(long id) {
    return gymRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find gym with id=%d", id)));
  }

  @Override
  public Gym createGym(Gym gym) {
    return gymRepository.save(gym);
  }

  @Override
  public Gym updateGym(Gym gym) {
    Gym savedGym = new Gym();
    Long id = gym.getId();
    if (Objects.nonNull(id)) {
      Gym targetGym = getGymById(id);
      if (updateNonEqualFields(gym, targetGym)) {
        savedGym = gymRepository.save(targetGym);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedGym;
  }

  @Override
  public void deleteGym(long id) {
    gymRepository.deleteById(id);

  }

  @Override
  public int getGymQuant() {
    return (int) gymRepository.count();
  }
}
