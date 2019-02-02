package com.danit.repositories.service;

import com.danit.models.User;
import com.danit.models.service.Tab;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TabRepository extends CrudRepository<Tab, Long> {

  Tab findByUserAndBaseEntityNameAndBaseEntityId(User user, String entityName, Long entityId);

  void deleteAllByUser_Username(String userName);

}
