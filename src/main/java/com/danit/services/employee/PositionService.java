package com.danit.services.employee;

import com.danit.models.employee.Position;

import java.util.List;

public interface PositionService {

  List<Position> getAllPositions();

  Position getPositionById(long id);

  Position createPosition(Position position);

  Position updatePosition(Position position);

  void deletePosition(long id);

  int getPositionQty();
}
