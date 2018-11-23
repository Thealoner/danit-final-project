package ua.com.danit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.danit.models.Contract;

import java.util.Set;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
  @Query("select c.id from Client c")
  Set<Long> getAllContractsId();
}
