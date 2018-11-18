package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.eployee.Department;
import com.danit.repositories.employee.DepartmentRepository;
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
public class DepartmentController {
  @Autowired
  private DepartmentRepository departmentRepository;

  @GetMapping("/department")
  public List<Department> retrieveAllDepartment() {
    return departmentRepository.findAll();
  }

  @GetMapping("/department/{id}")
  public Department retrieveDepartment(@PathVariable long id) {
    Optional<Department> department = departmentRepository.findById(id);

    if (!department.isPresent()) {
      throw new EntityNotFoundException("Department id-" + id);
    }
    return department.get();
  }

  @DeleteMapping("/department/{id}")
  public void deleteDepartment(@PathVariable long id) {
    departmentRepository.deleteById(id);
  }

  @PostMapping("/department")
  public ResponseEntity<Object> createDepartment(@RequestBody Department department) {
    Department savedDepartment = departmentRepository.save(department);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedDepartment.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/department/{id}")
  public ResponseEntity<Object> updateDepartment(@RequestBody Department department, @PathVariable long id) {

    Optional<Department> departmentOptional = departmentRepository.findById(id);

    if (!departmentOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    department.setId(id);

    departmentRepository.save(department);

    return ResponseEntity.noContent().build();
  }
}