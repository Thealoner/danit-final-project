package com.danit.repositories.service;

import com.danit.models.service.Tab;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TabRepository extends CrudRepository<Tab, Long> {

  Tab findByUserIdAndBaseEntityNameAndBaseEntityId(Long userId, String entityName, Long entityId);

  void deleteAllByUserId(Long userId);

  Tab findTopByBaseEntityNameAndBaseEntityIdOrderByCreationDateDesc(String entityName, Long entityId);

}
