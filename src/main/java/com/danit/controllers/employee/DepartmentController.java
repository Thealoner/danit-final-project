package com.danit.controllers.employee;

import com.danit.models.employee.Department;
import com.danit.services.employee.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class DepartmentController {

  private DepartmentService departmentService;

  @Autowired
  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping("/department")
  public List<Department> retrieveAllDepartment() {
    return departmentService.getAllDepartments();
  }

  @GetMapping("/department/{id}")
  public Department retrieveDepartment(@PathVariable long id) {
    return departmentService.getDepartmentById(id);

  }

  @DeleteMapping("/department/{id}")
  public void deleteDepartment(@PathVariable long id) {
    departmentService.deleteDepartment(id);
  }

  @PostMapping("/department")
  public Department createDepartment(@RequestBody Department department) {
    return departmentService.saveDepartment(department);

  }

  @PutMapping("/department/{id}")
  public Department updateDepartment(@RequestBody Department department, @PathVariable long id) {

    return departmentService.updateDepartment(department);

  }
}