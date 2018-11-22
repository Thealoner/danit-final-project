package com.danit.services.employee;

import com.danit.models.employee.Department;

import java.util.List;

public interface DepartmentService {

  List<Department> getAllDepartments();

  Department getDepartmentById(long id);

  Department saveDepartment(Department department);

  Department updateDepartment(Department department);

  void deleteDepartment(long id);

  boolean departmentExists(long id);

}
