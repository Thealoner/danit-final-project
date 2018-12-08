package com.danit.repositories;

import com.danit.models.ServiceCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends EntityRepository<ServiceCategory> {

  //  @Modifying
  //  @Transactional
  //  @Query(value = "DELETE FROM SERVICES_SERVICE_CATEGORIES  " +
  //      "WHERE SERVICES_ID = :serviceId " +
  //      "AND SERVICE_CATEGORIES_ID = :serviceCategoryId",
  //      nativeQuery = true)
  //  void deleteServiceCategoryService(@Param("serviceCategoryId") Long serviceCategoryId,
  // @Param("serviceId") Long serviceId);

  //  @Query("select count(*) from ServiceCategory")
  //  int getTotalQuantityOfServiceCategories();
}
