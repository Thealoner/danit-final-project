package com.danit.repositories;

import com.danit.models.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends BaseEntityRepository<Card> {

  Page<Card> findAllByContract_Id(Long contractId, Pageable pageable);

}

