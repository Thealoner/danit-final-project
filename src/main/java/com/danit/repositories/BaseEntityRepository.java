package com.danit.repositories;

import com.danit.models.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface BaseEntityRepository<E extends BaseEntity> extends CrudRepository<E, Long>, JpaSpecificationExecutor<E> {

  @Query("select e from #{#entityName} e where e.id in :idList")
  List<E> findAllEntitiesByIds(@Param(value = "idList") List<Long> idList);

  Page<E> findAll(Specification<E> spec, Pageable pageable);

  Page<E> findAll(Pageable pageable);

}
