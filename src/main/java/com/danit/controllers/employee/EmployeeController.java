package com.danit.controllers.employee;

import com.danit.models.employee.Employee;
import com.danit.services.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
public class EmployeeController {

  private EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/employee")
  public List<Employee> getAllEmployee(Pageable pageable, Principal principal) {
    return employeeService.getAllEmployees();
  }

  @GetMapping("/employee/{id}")
  public Employee getEmployeeById(@PathVariable(name = "id") long id, Principal principal) {
    return employeeService.getEmployeeById(id);
  }

  @DeleteMapping("/employee/{id}")
  public void deleteEmployee(@PathVariable(name = "id") long id, Principal principal) {
    employeeService.deleteEmployee(id);
  }

  @PostMapping("/employee")
  public Employee createEmployee(@RequestBody Employee employee, Principal principal) {
    return employeeService.createEmployee(employee);
  }

  @PutMapping("/employee/{id}")
  public Employee updateEmployee(@RequestBody Employee employee, Principal principal, @PathVariable long id) {
    return employeeService.updateEmployee(employee);
  }
}