package com.danit.repositories;


import com.danit.models.Paket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PaketRepository extends JpaRepository<Paket, Long> {
  @Query("select p.id from Paket p")
  Set<Long> getAllPaketsId();
}
