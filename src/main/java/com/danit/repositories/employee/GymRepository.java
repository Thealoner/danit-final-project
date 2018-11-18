package com.danit.repositories.employee;

import com.danit.models.eployee.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
}
