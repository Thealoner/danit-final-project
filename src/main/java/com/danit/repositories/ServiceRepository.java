package com.danit.repositories;

import com.danit.models.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ServiceRepository extends EntityRepository<Services> {
  @Query("select s.id from Services s")
  Set<Long> getAllServicesId();

//  @Query("select count(*) from Services")
//  int getTotalQuantityOfServices();

}
