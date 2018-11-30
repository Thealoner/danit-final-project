package com.danit.services.employee;

import com.danit.models.employee.EmployeeCategory;

import java.util.List;

public interface EmployeeCategoryService {

  List<EmployeeCategory> getAllEmployeeCategories();

  EmployeeCategory getEmployeeCategoryById(long id);

  EmployeeCategory createEmployeeCategory(EmployeeCategory employeeCategory);

  EmployeeCategory updateEmployeeCategory(EmployeeCategory employeeCategory);

  void deleteEmployeeCategory(long id);

  int getEmployeeCategoryQty();
}
