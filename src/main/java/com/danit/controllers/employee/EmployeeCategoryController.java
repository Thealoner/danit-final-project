package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.employee.EmployeeCategory;
import com.danit.repositories.employee.EmployeeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeCategoryController {
  @Autowired
  private EmployeeCategoryRepository employeeCategoryRepository;

  @GetMapping("/employee_category")
  public List<EmployeeCategory> retrieveAllCategory() {
    return employeeCategoryRepository.findAll();
  }

  @GetMapping("/employee_category/{id}")
  public EmployeeCategory retrieveCategory(@PathVariable long id) {
    Optional<EmployeeCategory> category = employeeCategoryRepository.findById(id);

    if (!category.isPresent()) {
      throw new EntityNotFoundException("Запись категории с id = " + id);
    }
    return category.get();
  }

  @DeleteMapping("/employee_category/{id}")
  public void deleteCategory(@PathVariable long id) {
    employeeCategoryRepository.deleteById(id);
  }

  @PostMapping("/employee_category")
  public ResponseEntity<Object> createCategory(@RequestBody EmployeeCategory employeeCategory) {
    EmployeeCategory savedEmployeeCategory = employeeCategoryRepository.save(employeeCategory);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedEmployeeCategory.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/employee_category/{id}")
  public ResponseEntity<Object> updateCategory(@RequestBody EmployeeCategory employeeCategory, @PathVariable long id) {

    Optional<EmployeeCategory> categoryOptional = employeeCategoryRepository.findById(id);

    if (!categoryOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    employeeCategory.setId(id);

    employeeCategoryRepository.save(employeeCategory);

    return ResponseEntity.noContent().build();
  }
}
