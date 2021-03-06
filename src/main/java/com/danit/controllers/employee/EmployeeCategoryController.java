package com.danit.controllers.employee;

import com.danit.models.employee.EmployeeCategory;
import com.danit.services.employee.EmployeeCategoryService;
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
public class EmployeeCategoryController {

  private EmployeeCategoryService employeeCategoryService;

  @Autowired
  public EmployeeCategoryController(EmployeeCategoryService employeeCategoryService) {
    this.employeeCategoryService = employeeCategoryService;
  }

  @GetMapping("/employee_category")
  public List<EmployeeCategory> getAllEmployeeCategories(Pageable pageable, Principal principal) {
    return employeeCategoryService.getAllEmployeeCategories();
  }

  @GetMapping("/employee_category/{id}")
  public EmployeeCategory getEmployeeCategoryById(@PathVariable long id, Principal principal) {
    return employeeCategoryService.getEmployeeCategoryById(id);

  }

  @DeleteMapping("/employee_category/{id}")
  public void deleteCategory(@PathVariable long id, Principal principal) {
    employeeCategoryService.deleteEmployeeCategory(id);
  }

  @PostMapping("/employee_category")
  public EmployeeCategory createCategory(@RequestBody EmployeeCategory employeeCategory, Principal principal) {
    return employeeCategoryService.createEmployeeCategory(employeeCategory);

  }

  @PutMapping("/employee_category/{id}")
  public EmployeeCategory updateCategory(@RequestBody EmployeeCategory employeeCategory, Principal principal,
                                         @PathVariable long id) {
    return employeeCategoryService.updateEmployeeCategory(employeeCategory);
  }
}
