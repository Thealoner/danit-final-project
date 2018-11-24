package com.danit.services.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.employee.Employee;
import com.danit.repositories.employee.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.danit.utils.ServiceUtils.updateNonEqualFields;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;

  EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee getEmployeeById(long id) {
    return employeeRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(String.format("Cant find employee with id=%d", id)));

  }

  @Override
  public void deleteEmployee(long id) {
    employeeRepository.deleteById(id);
  }

  @Override
  public Employee createEmployee(Employee enmployee) {
    return employeeRepository.save(enmployee);
  }

  @Override
  public Employee updateEmployee(Employee employee) {
    Employee savedEmployee = new Employee();
    Long id = employee.getId();
    if (Objects.nonNull(id)) {
      Employee targetEmployee = getEmployeeById(id);
      if (updateNonEqualFields(employee, targetEmployee)) {
        savedEmployee = employeeRepository.save(targetEmployee);
      }
    } else {
      throw new EntityParticularDataException("id field is empty");

    }
    return savedEmployee;
  }

  @Override
  public int getEmployeeQuant() {
    return (int) employeeRepository.count();
  }
}
