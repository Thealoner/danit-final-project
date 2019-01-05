package com.danit.repositories;

import com.danit.models.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends BaseEntityRepository<Card> {

  @Query("select c from Card c where c.contract.id = :contractId")
  Page<Card> findAllCardsForContractId(@Param(value = "contractId") Long contractId, Pageable pageable);

}

