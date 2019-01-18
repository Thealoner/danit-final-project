package com.danit.repositories;

import com.danit.models.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public interface ContractRepository extends BaseEntityRepository<Contract> {

  Page<Contract> findAllByClient_Id(Long clientId, Pageable pageable);

  Page<Contract> findAllByPaket_Id(Long paketId, Pageable pageable);

}
