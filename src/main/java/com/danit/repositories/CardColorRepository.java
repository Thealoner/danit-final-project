package com.danit.repositories;

import com.danit.models.CardColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CardColorRepository extends JpaRepository<CardColor, Long> {
  @Query("select c.id from CardColor c")
  Set<Long> getAllCardColorsId();
}

