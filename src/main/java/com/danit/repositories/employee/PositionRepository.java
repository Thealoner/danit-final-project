package com.danit.repositories.employee;

import com.danit.models.employee.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
