package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.eployee.Employee;
import com.danit.repositories.employee.EmployeeRepository;
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
public class EmployeeController {
  @Autowired
  private EmployeeRepository employeeRepository;

  @GetMapping("/employee")
  public List<Employee> retrieveAllEmployee() {
    return employeeRepository.findAll();
  }

  @GetMapping("/employee/{id}")
  public Employee retrieveStudent(@PathVariable long id) {
    Optional<Employee> employee = employeeRepository.findById(id);

    if (!employee.isPresent()) {
      throw new EntityNotFoundException("id-" + id);
    }
    return employee.get();
  }

  @DeleteMapping("/employee/{id}")
  public void deleteStudent(@PathVariable long id) {
    employeeRepository.deleteById(id);
  }

  @PostMapping("/employee")
  public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
    Employee savedEmployee = employeeRepository.save(employee);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedEmployee.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/employee/{id}")
  public ResponseEntity<Object> updateStudent(@RequestBody Employee employee, @PathVariable long id) {

    Optional<Employee> studentOptional = employeeRepository.findById(id);

    if (!studentOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    employee.setId(id);

    employeeRepository.save(employee);

    return ResponseEntity.noContent().build();
  }
}