package ua.com.danit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.danit.models.Card;

import java.util.Set;

@Repository
public interface CardColorRepository extends JpaRepository<Card, Long> {
  @Query("select c.id from Card c")
  Set<Long> getAllCardColorsId();
}

