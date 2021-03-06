package com.danit.repositories.service;

import com.danit.models.service.Tab;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TabRepository extends CrudRepository<Tab, Long> {

  Tab findTopByUserIdAndBaseEntityNameAndBaseEntityId(Long userId, String entityName, Long entityId);

  void deleteAllByUserId(Long userId);

  Tab findTopByBaseEntityNameAndBaseEntityIdOrderByCreationDateAsc(String entityName, Long entityId);

  List<Tab> findByBaseEntityNameAndBaseEntityIdOrderByCreationDateAsc(String entityName, Long entityId);

}
