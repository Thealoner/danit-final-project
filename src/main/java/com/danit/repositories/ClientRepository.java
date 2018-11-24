package com.danit.repositories;

import com.danit.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface ClientRepository extends CrudRepository<Client, Long>, JpaSpecificationExecutor<Client> {
  @Query("select c.id from Client c")
  Set<Long> getAllClientsId();

  @Query("select count(*) from Client")
  int getNumberOfClients();

  Page<Client> findAll(Specification<Client> spec, Pageable pageable);

  Page<Client> findAll(Pageable pageable);
}
