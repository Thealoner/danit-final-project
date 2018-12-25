package com.danit.repositories;

import com.danit.models.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends BaseEntityRepository<Service> {

  @Query("select s from Service s left join s.serviceCategories sc where sc.id = :serviceCategoryId")
  Page<Service> findAllServicesForServiceCategoryId(@Param(value = "serviceCategoryId") Long serviceCategoryId,
                                                    Pageable pageable);


}
