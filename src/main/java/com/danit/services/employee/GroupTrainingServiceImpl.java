package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.GroupTraining;
import com.danit.repositories.employee.GroupTrainingRepository;
import com.danit.utils.ServiceUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GroupTrainingServiceImpl implements GroupTrainingService {

  private ServiceUtils serviceUtils;

  private GroupTrainingRepository groupTrainingRepository;

  public GroupTrainingServiceImpl(ServiceUtils serviceUtils, GroupTrainingRepository groupTrainingRepository) {
    this.serviceUtils = serviceUtils;
    this.groupTrainingRepository = groupTrainingRepository;
  }

  @Override
  public List<GroupTraining> getAllGroupTrainings() {
    return groupTrainingRepository.findAll();
  }

  @Override
  public GroupTraining getGroupTrainingById(long id) {
    return groupTrainingRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find group training with id=%d", id)));

  }

  @Override
  public GroupTraining createGroupTraining(GroupTraining groupTraining) {
    return groupTrainingRepository.save(groupTraining);
  }

  @Override
  public GroupTraining updateGroupTraining(GroupTraining groupTraining) {
    GroupTraining savedGroupTraining = new GroupTraining();
    Long id = groupTraining.getId();
    if (Objects.nonNull(id)) {
      GroupTraining targetGroupTraining = getGroupTrainingById(id);
      if (serviceUtils.updateNonEqualFields(groupTraining, targetGroupTraining)) {
        savedGroupTraining = groupTrainingRepository.save(targetGroupTraining);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedGroupTraining;
  }

  @Override
  public void deleteGroupTraining(long id) {
    groupTrainingRepository.deleteById(id);

  }
}
