package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.TrainingType;
import com.danit.repositories.employee.TrainingTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.danit.utils.ServiceUtils.updateNonEqualFields;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

  private TrainingTypeRepository trainingTypeRepository;

  public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository) {
    this.trainingTypeRepository = trainingTypeRepository;
  }

  @Override
  public List<TrainingType> getAllTrainingTypes() {
    return trainingTypeRepository.findAll();
  }

  @Override
  public TrainingType getTrainingTypeById(long id) {
    return trainingTypeRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find training type with id=%d", id)));
  }

  @Override
  public TrainingType createTrainingType(TrainingType trainingType) {
    return trainingTypeRepository.save(trainingType);
  }

  @Override
  public TrainingType updateTrainingType(TrainingType trainingType) {
    TrainingType savedTrainingType = new TrainingType();
    Long id = trainingType.getId();
    if (Objects.nonNull(id)) {
      TrainingType targetTrainingType = getTrainingTypeById(id);
      if (updateNonEqualFields(trainingType, targetTrainingType)) {
        savedTrainingType = trainingTypeRepository.save(targetTrainingType);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedTrainingType;
  }

  @Override
  public void deleteTrainingType(long id) {
    trainingTypeRepository.deleteById(id);

  }

  @Override
  public boolean trainingTypeExists(long id) {
    return false;
  }
}
