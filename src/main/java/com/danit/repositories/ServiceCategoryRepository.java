package com.danit.repositories;

import com.danit.models.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM SERVICES_SERVICE_CATEGORIES  " +
      "WHERE SERVICES_ID = :serviceId " +
      "AND SERVICE_CATEGORIES_ID = :serviceCategoryId",
      nativeQuery = true)
  void deleteServiceCategoryService(@Param("serviceCategoryId") Long serviceCategoryId, @Param("serviceId") Long serviceId);
}
