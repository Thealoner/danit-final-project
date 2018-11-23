package ua.com.danit.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.danit.models.Client;

import java.util.Set;


@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
  @Query("select c.id from Client c")
  Set<Long> getAllClientsId();

  @Query("select count(*) from Client")
  int getNumberOfClients();

  Page<Client> findAll(Pageable pageable);
}
