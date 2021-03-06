package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.Position;
import com.danit.repositories.employee.PositionRepository;
import com.danit.utils.ServiceUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class PositionServiceImpl implements PositionService {

  private ServiceUtils serviceUtils;

  private PositionRepository positionRepository;

  public PositionServiceImpl(ServiceUtils serviceUtils, PositionRepository positionRepository) {
    this.serviceUtils = serviceUtils;
    this.positionRepository = positionRepository;
  }

  @Override
  public List<Position> getAllPositions() {
    return positionRepository.findAll();
  }

  @Override
  public Position getPositionById(long id) {
    return positionRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find position with id=%d", id)));
  }

  @Override
  public Position createPosition(Position position) {
    return
        positionRepository.save(position);
  }

  @Override
  public Position updatePosition(Position position) {
    Position savedPosition = new Position();
    Long id = position.getId();
    if (Objects.nonNull(id)) {
      Position targetPosition = getPositionById(id);
      if (serviceUtils.updateNonEqualFields(position, targetPosition)) {
        savedPosition = positionRepository.save(targetPosition);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedPosition;
  }

  @Override
  public void deletePosition(long id) {
    positionRepository.deleteById(id);

  }

  @Override
  public int getPositionQty() {
    return (int) positionRepository.count();
  }
}
