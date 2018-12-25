package com.danit.repositories;

import com.danit.models.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ContractRepository extends BaseEntityRepository<Contract> {

  @Query("select c from Contract c where c.client.id = :clientId")
  Page<Contract> findAllContractsForClientId(@Param(value = "clientId") Long clientId, Pageable pageable);

  @Query("select c from Contract c where c.paket.id = :paketId")
  Page<Contract> findAllContractsForPaketId(@Param(value = "paketId") Long paketId, Pageable pageable);

}
