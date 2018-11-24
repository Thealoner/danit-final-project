package com.danit.repositories;

import com.danit.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
  @Query("select c.id from Client c")
  Set<Long> getAllClientsId();

  @Query("select count(*) from Client")
  int getNumberOfClients();

  @Query("select c from Client c where c.lastName like '%:filter%' or "
      + "c.firstName like '%:filter%'")
  Page<Client> findAll(@Param("filter") String filter, Pageable pageable);

  Page<Client> findAll(Pageable pageable);
}
