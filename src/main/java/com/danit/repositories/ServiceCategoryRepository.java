package com.danit.repositories;

import com.danit.models.ServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends BaseEntityRepository<ServiceCategory> {

  @Query("select sc from ServiceCategory sc left join sc.services s where s.id = :serviceId")
  Page<ServiceCategory> findAllServiceCategoriesOfServiceWithId(@Param(value = "serviceId") Long serviceId,
                                                                Pageable pageable);
}
