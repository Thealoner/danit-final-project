package com.danit.repositories;


import com.danit.models.Paket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PaketRepository extends CrudRepository<Paket, Long>, JpaSpecificationExecutor<Paket> {

  @Query("select p.id from Paket p")
  Set<Long> getAllPaketsId();

  @Query("select count(*) from Paket")
  int getNumberOfPakets();

  Page<Paket> findAll(Specification<Paket> spec, Pageable pageable);

  Page<Paket> findAll(Pageable pageable);

}
