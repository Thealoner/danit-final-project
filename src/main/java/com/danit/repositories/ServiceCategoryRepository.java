package com.danit.repositories;

import com.danit.models.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {

  @Query("delete s.serviceCategories " +
      "from Services s " +
      "where s.serviceCategories.id = :serviceCategoryId " + "and s.id = :serviceId")
  void deleteServiceCategoryService(@Param("serviceCategoryId") Long serviceCategoryId, @Param("serviceId")Long serviceId);
}
