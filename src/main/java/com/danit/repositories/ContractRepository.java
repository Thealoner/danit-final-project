package com.danit.repositories;

import com.danit.models.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {
  @Query("select c.id from Client c")
  Set<Long> getAllContractsId();

  Page<Contract> findAll(Pageable pageable);

}
