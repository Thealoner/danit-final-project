package com.danit.repositories;

import com.danit.models.ServiceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends BaseEntityRepository<ServiceCategory> {

  Page<ServiceCategory> findAllByServices_Id(Long serviceId, Pageable pageable);

}
