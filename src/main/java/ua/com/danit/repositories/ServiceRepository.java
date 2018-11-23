package ua.com.danit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.danit.models.Services;

import java.util.Set;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {
  @Query("select s.id from Services s")
  Set<Long> getAllServicesId();

  @Query("select count(*) from Services")
  int getTotalQuantityOfServices();

}
