package com.danit.services.employee;

import com.danit.models.employee.Employee;

import java.util.List;

public interface EmployeeService {

  List<Employee> getAllEmployees();

  Employee getEmployeeById(long id);

  void deleteEmployee(long id);

  Employee createEmployee(Employee emp);

  Employee updateEmployee(Employee emp);

  int getEmployeeQuant();

}

