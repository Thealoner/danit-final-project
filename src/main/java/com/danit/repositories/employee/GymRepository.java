package com.danit.repositories.employee;

import com.danit.models.employee.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
}
