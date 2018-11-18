package com.danit.repositories.employee;

import com.danit.models.employee.EmployeeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCategoryRepository extends JpaRepository<EmployeeCategory, Long> {
}
